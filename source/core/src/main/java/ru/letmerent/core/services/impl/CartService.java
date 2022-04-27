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
import ru.letmerent.core.dto.InstrumentDto;
import ru.letmerent.core.dto.InstrumentForListDto;
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

    @Value("${properties.cart.prefix}")
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
        String itemsString = lhm.get("items").toString().replaceAll("=", ":");
        System.out.println(itemsString);
        JSONArray jsonArray = new JSONArray(itemsString);
        System.out.println(jsonArray);
        System.out.println(jsonArray.length());

        BigDecimal totalFee = null;
        BigDecimal totalPrice = null;
        if (lhm.get("totalFee") != null) {
            totalFee = BigDecimal.valueOf(Double.parseDouble(lhm.get("totalFee").toString()));
        }
        System.out.println(totalFee);
        if (lhm.get("totalPrice") != null) {
            totalPrice = BigDecimal.valueOf(Double.parseDouble(lhm.get("totalPrice").toString()));
        }
        System.out.println(totalPrice);

        Cart cart = new Cart();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsoOrderItem = (JSONObject) jsonArray.get(0);
            System.out.println(jsoOrderItem);
            JSONObject jsoInstrumentDto = jsoOrderItem.getJSONObject("instrument");
            System.out.println(jsoInstrumentDto);
            InstrumentDto iDto = new InstrumentForListDto();
            iDto.setId(jsoInstrumentDto.getLong("id"));
            iDto.setTitle(jsoInstrumentDto.getString("title"));
            iDto.setBrandName(jsoInstrumentDto.getString("brandName"));
            iDto.setPrice(jsoInstrumentDto.getBigDecimal("price"));
            iDto.setFee(jsoInstrumentDto.getBigDecimal("fee"));
            iDto.setOwnerUsername(jsoInstrumentDto.getString("ownerUsername"));
            iDto.setCategoryName(jsoInstrumentDto.getString("categoryName"));

            OrderItemDto dto = new OrderItemDto();
            if (!jsoOrderItem.get("id").toString().equals("null")) {
                dto.setId(jsoOrderItem.getLong("id"));
            }
            dto.setStartDate(convertStringDateToLocalDateTime(jsoOrderItem.getString("startDate")));
            dto.setEndDate(convertStringDateToLocalDateTime(jsoOrderItem.getString("endDate")));
            dto.setInstrument(iDto);
            dto.setRentPrice(jsoOrderItem.getBigDecimal("rentPrice"));
            dto.setRentLength(jsoOrderItem.getLong("rentLength"));

            cart.setTotalPrice(totalPrice);
            cart.setTotalFee(totalFee);

            cart.getItems().add(dto);
        }


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
