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

    @Schema(description = "Логин пользователя, оформившего аренду")
    String renterName;
    
    @Schema(description = "Id пользователя, оформившего аренду")
    Long renterId;
    
    public InstrumentRentDto(LocalDateTime dateStart, LocalDateTime dateFinish, String renterName, Long renterId) {
       super(dateStart,dateFinish);
        this.renterName = renterName;
        this.renterId = renterId;
    }
}
