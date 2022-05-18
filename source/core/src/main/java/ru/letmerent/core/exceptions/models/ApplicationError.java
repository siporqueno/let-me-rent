package ru.letmerent.core.exceptions.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Schema(description = "Модель сообщений об ошибке")
@Component
public class ApplicationError {

    @Schema(description = "Наименование сервиса", example = "Authentication")
    private String serviceName;

    @Schema(description = "Код ошибки", example = "400")
    private Integer errorCode;

    @Schema(description = "Описание ошибки", example = "Bad credentials")
    private Object userMessage;

    @Schema(description = "Дата возникновения ошибки", example = "2022-04-22T10:00:17.851+00:00")
    private String date;

    public ApplicationError generateError(Integer errorCode, Object message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return ApplicationError.builder()
                .errorCode(errorCode)
                .userMessage(message)
                .date(dateFormat.format(new Date()))
                .build();
    }
}
