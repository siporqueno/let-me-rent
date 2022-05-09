package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель инструмента со списком")
public class InstrumentRentDto extends IntervalDto {

    //TODO: поскольку с фронта мы из списка аренд можем отправить отзыв, а для этого отзыва нужен id арендатора, надо в эту DTO-шку добавить еще поле userId
    
    @Schema(description = "Логин пользователя, оформившего аренду")
    String username;
    
    public InstrumentRentDto(LocalDateTime dateStart, LocalDateTime dateFinish, String username) {
       super(dateStart,dateFinish);
        this.username = username;
    }
}
