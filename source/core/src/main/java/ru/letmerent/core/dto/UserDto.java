package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель пользователя")
@ToString
@Builder
public class UserDto {//TODO: Разобраться с валидацией: он работает как-то криво (в телеграмме отправила фото)

    @Schema(description = "Идентификатор пользователя", example = "12345678")
    Long id;

    @Schema(description = "Имя пользователя", example = "Александр")
    String firstName;

    @Schema(description = "Отчество пользователя", example = "Родионович")
    String secondName;

    @Schema(description = "Фамилия пользователя", example = "Бородач")
    String lastName;

    @NotNull(message = "Заполните адрес электронной почты. Данное поле не может быть пустым")
    @Schema(description = "Электронная почта", example = "super_boroda@gmail.com", required = true)
    String email;

    @NotNull(message = "Укажите логин. Данное поле не может быть пустым")
    @Schema(description = "Уникальное наименование пользователя", example = "super_boroda", required = true)
    String userName;

    @NotNull(message = "Укажите пароль. Данное поле не может быть пустым")
            @Schema(description = "Пароль пользователя", example = "111111", required = true)
    String password;

    @NotNull(message = "Укажите подтерждение пароля. Данное поле не может быть пустым")
    @Schema(description = "Подтверждение пароля пользователя", example = "111111", required = true)
    String passwordConfirmation;

    @Schema(description = "Список ролей пользователя", implementation = List.class)
    Collection<String> roles;
}
