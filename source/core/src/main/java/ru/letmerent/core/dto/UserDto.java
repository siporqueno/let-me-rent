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
public class UserDto {

    @Schema(description = "Идентификатор пользователя", example = "12345678")
    Long id;

    @Schema(description = "Имя пользователя", example = "Александр")
    String firstName;

    @Schema(description = "Отчество пользователя", example = "Родионович")
    String secondName;

    @Schema(description = "Фамилия пользователя", example = "Бородач")
    String lastName;

    @NotNull
    @Schema(description = "Электронная почта", example = "super_boroda@gmail.com", required = true)
    String email;

    @NotNull
    @Schema(description = "Уникальное наименование пользователя", example = "super_boroda", required = true)
    String userName;

    @NotNull
            @Schema(description = "Пароль пользователя", example = "111111", required = true)
    String password;

    @NotNull
    @Schema(description = "Подтверждение пароля пользователя", example = "111111", required = true)
    String passwordConfirmation;

    @Schema(description = "Список ролей пользователя", implementation = List.class)
    Collection<String> roles;
}
