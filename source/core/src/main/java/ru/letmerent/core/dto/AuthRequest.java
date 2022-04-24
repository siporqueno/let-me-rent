package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос на авторизацию")
public class AuthRequest {

    @NotNull
    @Schema(description = "Имя пользователя", example = "user")
    private String username;

    @Schema(description = "Пароль пользователя", example = "111111")
    @NotNull
    private String password;
}
