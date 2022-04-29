package ru.letmerent.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Корзина")
@AllArgsConstructor
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

    @JsonIgnore
    public void add(InstrumentForListDto instrument, LocalDateTime startDate, LocalDateTime endDate) {
        for (OrderItemDto oi : items) {
            if (oi.getInstrument().getId().equals(instrument.getId())) {
                return;
            }
        }

        items.add(new OrderItemDto(instrument, startDate, endDate));
        recalculate();
    }

    @JsonIgnore
    public void remove(Long instrumentId) {
        items.removeIf(i -> i.getInstrument().getId().equals(instrumentId));
        recalculate();
    }

    @JsonIgnore
    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
        totalFee = BigDecimal.ZERO;
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        totalFee = BigDecimal.ZERO;
        for (OrderItemDto i : items) {
            totalFee = totalFee.add(i.getRentPrice());
            totalPrice = totalPrice.add(i.getInstrument().getPrice());
        }
    }

    @JsonIgnore
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
