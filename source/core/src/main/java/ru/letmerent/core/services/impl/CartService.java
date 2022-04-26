package ru.letmerent.core.services.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.pro.packaged.B;
import lombok.RequiredArgsConstructor;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.letmerent.core.converters.InstrumentConverter;
import ru.letmerent.core.dto.Cart;
import ru.letmerent.core.dto.OrderItemDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final InstrumentServiceImpl instrumentService;
    private final InstrumentConverter instrumentConverter;
    private final ObjectMapper objectMapper;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }

        Map<String, Object> lhm = (Map<String, Object>) redisTemplate.opsForValue().get(cartKey);
        String itemsString = lhm.get("items").toString();
        System.out.println(itemsString);
        JSONArray jsonArray = new JSONArray(itemsString);

        BigDecimal totalFee = null;
        BigDecimal totalPrice = null;
        if (lhm.get("totalFee") != null) {
            totalFee = new BigDecimal((String) lhm.get("totalFee"));
        }
        if (lhm.get("totalPrice") != null) {
            totalPrice = new BigDecimal((String) lhm.get("totalPrice"));
        }
        System.out.println(totalFee);
        System.out.println(totalPrice);

        Cart cart = null;

//        if (jsonItems.length() == 0) {
            cart = new Cart();
//        } else System.out.println("Корзина не пуста");

//        return (Cart) redisTemplate.opsForValue().get(cartKey);
        return cart;
    }

    public void addToCart(String cartKey, Long instrumentId, String startDate, String endDate) {
        execute(cartKey, c -> {
            c.add(instrumentConverter.toListDto(instrumentService.getInstrumentById(instrumentId)),
                    convertStringDateToLocalDateTime(startDate), convertStringDateToLocalDateTime(endDate));
        });
    }

    private LocalDateTime convertStringDateToLocalDateTime(String date) {
        String[] dates = date.split("-", 3);
        int year = Integer.parseInt(dates[2]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[0]);
        return LocalDateTime.of(year, month, day, 00, 00);
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long instrumentId) {
        execute(cartKey, c -> c.remove(instrumentId));
    }

    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}
