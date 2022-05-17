package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Информация о модели инструмента")
@ToString
public class InstrumentInfoDto extends InstrumentDto {

    @Schema(description = "Id владельца", example = "1")
    Long ownerId;

    @Schema(description = "Имя владельца", example = "Александр")
    String ownerFirstName;

    @Schema(description = "Отчество владельца", example = "Родионович")
    String ownerSecondName;

    @Schema(description = "Фамилия владельца", example = "Бородач")
    String ownerLastName;

    @Schema(description = "Электронная почта", example = "super_boroda@gmail.com")
    String ownerEmail;

    @Schema(description = "Краткое описание инструмента", example = "Супер инструмент, одной рукой держу, другой слёзы счастья вытираю")
    String description;

    @Schema(description = "Интервалы доступности")
    List<IntervalDto> intervals;

    @Schema(description = "список URL картинок")
    Collection<String> pictureUrls;

    @Schema(description = "Дата постановки инструмента в аренду")
    LocalDateTime startDate;

    @Schema(description = "Дата снятия инструмента с аренды")
    LocalDateTime endDate;
}
