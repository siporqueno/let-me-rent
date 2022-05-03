package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.letmerent.core.entity.Picture;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель инструмента")
@ToString
public class InstrumentDto implements Serializable {

    @Schema(description = "Идентификатор инструмента", example = "12345678")
    Long id;

    @Schema(description = "Название инструмента", example = "Перфоратор")
    String title;

    @Schema(description = "Бренд инструмента", example = "Bosch")
    String brandName;

    @Schema(description = "Цена инструмента", example = "1000000")
    BigDecimal price;

    @Schema(description = "Платёж за аренду", example = "1000000")
    BigDecimal fee; // в профиле пока не фигурирует, но, может, добавим

    @Schema(description = "Username владельца", example = "borodach")
    String ownerUsername;

    @Schema(description = "Название категории", example = "Дрели")
    String categoryName; // в профиле пока не фигурирует, но, может, добавим
    
    @Schema(description = "список картинок")
    Collection<Picture> pictures;
}
