package ru.letmerent.core.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstrumentCommentDto {

    private Long commentId;

    private Long userId;

    private Long instrumentId;

    private String description;

    private Integer grade;

    private Date startDate;

    private Date updateDate;

    private Date endDate;
}
