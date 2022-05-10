package ru.letmerent.core.dto.comments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Комментарий о пользователе")
public class UserCommentDto {

    @Schema(description = "Идентификатор комментария", example = "1")
    private Long commentId;

    @Schema(description = "Идентификатор пользователя, оставившего комментарий", example = "1")
    private Long userId;

    @Schema(description = "Идентификатор пользователя, о котором оставлен комментарий", example = "1")
    private Long aboutUserId;

    @Schema(description = "Описание комментария", defaultValue = "Хороший инструмент, рекомендую")
    private String description;

    @Nullable
    @Min(0)
    @Max(5)
    @Schema(description = "Оценка", example = "3")
    private Integer grade;

    @Schema(description = "Дата комментария", example = "2015-11-21T12:48:00.973")
    private Date startDate;

    @Schema(description = "Дата обновления комментария", example = "2015-11-21T12:48:00.973")
    private Date updateDate;

}
