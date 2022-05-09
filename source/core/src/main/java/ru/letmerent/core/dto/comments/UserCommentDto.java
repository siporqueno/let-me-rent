package ru.letmerent.core.dto.comments;

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
public class UserCommentDto {

    private Long commentId;

    private Long userId;

    private Long aboutUserId;

    private String description;

    @Nullable
    @Min(0)
    @Max(5)
    private Integer grade;

    private Date startDate;

    private Date updateDate;

    private Date endDate;
}
