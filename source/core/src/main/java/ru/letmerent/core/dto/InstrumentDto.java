package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @Schema(description = "Красткое описание инструмента", example = "Супер инструмент, одной рукой держу, другой слёзы счастья вытираю")
    String description;

    @Schema(description = "Цена инструмента", example = "1000000")
    BigDecimal price;

    @Schema(description = "Платёж за аренду", example = "1000000")
    BigDecimal fee;

    @Schema(description = "Модель пользователя-владельца")
    UserDto owner;

    @Schema(description = "Список URL-ссылок на изображения")
    List<String> picturesUrls;

    @Schema(description = "URL-ссылка на аватар")
    String avatarPictureUrl;

    @Schema(description = "Название категории")
    String categoryName;

    @Schema(description = "Список интервалов занятости данного инструмента")
    List<LocalDateTime> intervals;
}
