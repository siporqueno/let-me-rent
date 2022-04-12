package ru.letmerent.core.dtos;

@NoArgsConstructor
@Data
public class OrderItemDto {

    private Long id;
    private LocalDateTime dateStart;
    private LocalDateTime dateFinish;
    private OrderDto order;
    private InstrumentDto instrument; // на ветке LMR-15 пока что поле List инструментов, но Николай правильно отметил, что
    //в одном OrderItem лежит один инструмент

    public OrderItemDto (OrderItem orderItem){
        this.id = orderItem.getId();
        this.dateStart = orderItem.getDateStart();
        this.dateFinish = orderItem.getDateFinish();
        this.order = new OrderDto(orderItem.getOrder());
        this.instrument = new InstrumentDto(orderItem.getInstrument());
    }

}
