package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель страницы")
@ToString
public class PageDto<T extends Serializable> implements Serializable {

    @Schema(description = "Список позиций на странице")
    List<T> instruments;

    @Schema(description = "Всего страниц", example = "5")
    int totalPages;

    @Schema(description = "Всего позиций", example = "100")
    long totalElements;
}
