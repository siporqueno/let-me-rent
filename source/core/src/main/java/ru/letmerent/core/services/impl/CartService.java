package ru.letmerent.core.services.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.letmerent.core.converters.InstrumentConverter;
import ru.letmerent.core.converters.UserConverter;
import ru.letmerent.core.dto.Cart;
import ru.letmerent.core.dto.OrderDto;
import ru.letmerent.core.dto.OrderItemDto;
import ru.letmerent.core.dto.UserDto;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final InstrumentServiceImpl instrumentService;
    private final InstrumentConverter instrumentConverter;
    private final UserService userService;
    private final UserConverter userConverter;
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

        return objectMapper.convertValue(redisTemplate.opsForValue().get(cartKey), Cart.class);
    }

    public void addToCart(String cartKey, Long instrumentId, String startDate, String endDate) {
        execute(cartKey, c -> {
            c.add(instrumentConverter.toListDto(instrumentService.getInstrumentById(instrumentId).get()),
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

    public OrderDto convertCartToOrder(Principal principal, String userCartKey){
        Cart cart = getCurrentCart(userCartKey);
        //FIXME предполагаем что все даты одинаковые
        LocalDateTime startDate = cart.getItems()
                .stream()
                .map(OrderItemDto::getStartDate)
                .findFirst()
                .orElseThrow();
        LocalDateTime endDate = cart.getItems()
                .stream()
                .map(OrderItemDto::getStartDate)
                .findFirst()
                .orElseThrow();
        UserDto user = userConverter.userToUserDtoConverter(userService.findByUsername(principal.getName()));
        OrderDto orderDto = new OrderDto();
        orderDto.setDateStart(startDate);
        orderDto.setDateFinish(endDate);
        orderDto.setRenter(user);
        orderDto.setOrderItems(cart.getItems());
        return orderDto;
    }

}
