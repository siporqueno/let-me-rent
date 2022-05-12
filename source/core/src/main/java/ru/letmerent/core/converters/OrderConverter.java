package ru.letmerent.core.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.letmerent.core.dto.OrderDto;
import ru.letmerent.core.dto.OrderItemDto;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.Order;
import ru.letmerent.core.entity.OrderItem;
import ru.letmerent.core.entity.User;
import ru.letmerent.core.services.InstrumentService;
import ru.letmerent.core.services.impl.UserService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class OrderConverter {

    private final UserService userService;
    private final InstrumentService instrumentService;
    private final UserConverter userConverter;
    private final InstrumentConverter instrumentConverter;

    public Order convertToOrder(OrderDto orderDto) {
        Order order = new Order();

        User user = userService.findByUsername(orderDto.getRenter().getUserName());
        order.setUser(user);

        order.setStartDate(orderDto.getDateStart());
        order.setEndDate(orderDto.getDateFinish());
        order.setRentTotalPrice(
                orderDto.getOrderItems().stream()
                        .map(OrderItemDto::getRentPrice)
                        .reduce(ZERO,BigDecimal::add)
        );
        order.setOrderItems(orderDto.getOrderItems().stream().map(this::convertToOrderItem).collect(toList()));

        return order;
    }

    public OrderItem convertToOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();

        instrumentService.getInstrumentById(orderItem.getInstrument().getId()).ifPresent(orderItem::setInstrument);
        orderItem.setStartDate(orderItemDto.getStartDate());
        orderItem.setEndDate(orderItemDto.getEndDate());
        orderItem.setRentPrice(orderItemDto.getRentPrice());

        return orderItem;
    }

    public OrderDto convertToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setRenter(userConverter.userToUserDtoConverter(order.getUser()));
        orderDto.setDateStart(order.getStartDate());
        orderDto.setDateFinish(order.getEndDate());
        orderDto.setOrderItems(order.getOrderItems().stream().map(this::convertToOrderItemDto).collect(toList()));

        return orderDto;
    }

    public OrderItemDto convertToOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setId(orderItemDto.getId());
        orderItemDto.setInstrument(instrumentConverter.toInstrumentInfoDto(orderItem.getInstrument()));
        orderItemDto.setRentPrice(orderItem.getRentPrice());
        orderItemDto.setStartDate(orderItem.getStartDate());
        orderItemDto.setEndDate(orderItem.getEndDate());

        return orderItemDto;
    }
}
