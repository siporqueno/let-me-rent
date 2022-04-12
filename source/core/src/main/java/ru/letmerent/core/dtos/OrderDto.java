package ru.letmerent.core.dtos;

import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class OrderDto {

    private Long id;
    private User renter;
    private LocalDateTime dateStart;
    private LocalDateTime dateFinish;
    private List<OrderItemDto> orderItems;


    public OrderDto (Order order){
        this.id = order.getId();
        this.renter = order.getRenter();
        this.dateStart = order.getDateStart();
        this.dateFinish = order.getDateFinish();
        this.orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }

}
