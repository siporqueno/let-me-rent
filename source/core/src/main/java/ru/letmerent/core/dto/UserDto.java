package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель пользователя")
@ToString
@Builder
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

    String password;

    String passwordConfirmation;

    Collection<String> roles;
}
