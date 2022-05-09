package ru.letmerent.core.converters;

import org.springframework.stereotype.Component;
import ru.letmerent.core.dto.comments.InstrumentCommentDto;
import ru.letmerent.core.dto.comments.UserCommentDto;
import ru.letmerent.core.entity.comments.AboutInstrumentComment;
import ru.letmerent.core.entity.comments.AboutUserComment;

@Component
public class CommentConverter {

    public AboutUserComment fromDtoToAboutUserComment(UserCommentDto commentDto) {
        return AboutUserComment.builder()
                .userId(commentDto.getUserId())
                .aboutUserId(commentDto.getAboutUserId())
                .description(commentDto.getDescription())
                .grade(commentDto.getGrade())
                .build();
    }

    public UserCommentDto fromAboutUserCommentToDto(AboutUserComment comment) {
        return UserCommentDto.builder()
                .commentId(comment.getCommentId())
                .userId(comment.getUserId())
                .aboutUserId(comment.getAboutUserId())
                .description(comment.getDescription())
                .grade(comment.getGrade())
                .startDate(comment.getStartDate())
                .updateDate(comment.getUpdateDate())
                .build();
    }

    public AboutInstrumentComment fromDtoToAboutInstrumentComment(InstrumentCommentDto commentDto) {
        return AboutInstrumentComment.builder()
                .userId(commentDto.getUserId())
                .instrumentId(commentDto.getInstrumentId())
                .description(commentDto.getDescription())
                .grade(commentDto.getGrade())
                .build();
    }

    public InstrumentCommentDto fromAboutInstrumentCommentToDto(AboutInstrumentComment comment) {
        return InstrumentCommentDto.builder()
                .commentId(comment.getCommentId())
                .userId(comment.getUserId())
                .instrumentId(comment.getInstrumentId())
                .description(comment.getDescription())
                .grade(comment.getGrade())
                .startDate(comment.getStartDate())
                .updateDate(comment.getUpdateDate())
                .build();
    }
}
