package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос на авторизацию")
public class AuthRequest {

    @NotNull
    @Size(max = 255)
    @Schema(description = "Имя пользователя", example = "user")
    private String username;

    @Schema(description = "Пароль пользователя", example = "111111")
    @NotNull
    @Size(min = 3, max = 16)
    private String password;
}
