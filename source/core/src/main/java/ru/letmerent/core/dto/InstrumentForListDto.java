package ru.letmerent.core.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель инструмента со списком")
@ToString
public class InstrumentForListDto extends InstrumentDto {

    @Schema(description = "URL-ссылка на аватар")
    String avatarPictureUrl;
}
