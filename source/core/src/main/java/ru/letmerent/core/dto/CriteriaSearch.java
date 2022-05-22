package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Критерии для поиска")
@ToString
public class CriteriaSearch {

    @Schema(description = "Имя категории инструмента")
    String categoryName;

    @Schema(description = "Название инструмента")
    String title;

    @Schema(description = "Имя владельца владельца")
    String ownerName;

    @Schema(description = "Максимальная цена аренды")
    BigDecimal maxFee;

    @Schema(description = "Дата начала предполагаемой аренды")
    String startDate;

    @Schema(description = "Дата конца предполагаемой аренды")
    String endDate;
}
