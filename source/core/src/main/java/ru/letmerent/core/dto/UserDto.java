package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель пользователя")
@ToString
public class UserDto {

    @Schema(description = "Идентификатор пользователя", example = "12345678")
    Long id;

    @Schema(description = "Имя пользователя", example = "Александр")
    String firstName;

    @Schema(description = "Отчество пользователя", example = "Родионович")
    String secondName;

    @Schema(description = "Фамилия пользователя", example = "Бородач")
    String lastName;

    @Schema(description = "Электронная почта", example = "super_boroda@gmail.com")
    String email;

    @Schema(description = "Уникальное наименование пользователя", example = "super_boroda")
    String userName;

    @Schema(description = "Список аренд пользователя")
    List<OrderDto> orders;
}
