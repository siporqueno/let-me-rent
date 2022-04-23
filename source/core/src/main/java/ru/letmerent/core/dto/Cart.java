package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Корзина")
public class Cart {
    @Schema(description = "Список позиций в корзине")
    private List<OrderItemDto> items;

    @Schema(description = "Стоимость аренды всех элементов корзины")
    private BigDecimal totalFee;

    @Schema(description = "Общая сумма залога за все элементы в корзине")
    private BigDecimal totalPrice;


    public Cart() {
        this.items = new ArrayList<>();
    }

//    public boolean add(Long instrumentId) {
//        for (OrderItemDto i : items) {
//            if (i.getProductId().equals(productId)) {
//                i.changeQuantity(1);
//                recalculate();
//                return true;
//            }
//        }
//        return false;
//    }

    public void add(InstrumentForListDto instrument, LocalDateTime startDate, LocalDateTime endDate) {
        items.add(new OrderItemDto(instrument,startDate,endDate));
        recalculate();
    }

    public void remove(Long instrumentId) {
        items.removeIf(i -> i.getInstrument().getId().equals(instrumentId));
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
        totalFee = BigDecimal.ZERO;
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        totalFee = BigDecimal.ZERO;
        for (OrderItemDto i : items) {
            totalFee.add(i.getRentPrice());
            totalPrice.add(i.getInstrument().getPrice());
        }
    }

    public void merge(Cart another) {
        for (OrderItemDto anotherItem : another.items) {
            for (OrderItemDto myItem : items) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clear();
    }

}
