package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель интервала времени")
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class IntervalDto {

    @Schema(description = "Время старта аренды", example = "2015-11-21T12:48:00.973")
    LocalDateTime dateStart;

    @Schema(description = "Время завершения аренды", example = "2015-11-21T12:48:00.973")
    LocalDateTime dateFinish;
}
