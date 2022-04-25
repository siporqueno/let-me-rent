package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

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

    @NotNull
    @Max(255)
    @Schema(description = "Электронная почта", example = "super_boroda@gmail.com")
    String email;

    @NotNull
    @Max(255)
    @Schema(description = "Уникальное наименование пользователя", example = "super_boroda")
    String userName;

    @NotNull
    @Min(3)
    @Max(12)
    String password;

    @NotNull
    @Min(3)
    @Max(12)
    String passwordConfirmation;

    Collection<String> roles;
}
