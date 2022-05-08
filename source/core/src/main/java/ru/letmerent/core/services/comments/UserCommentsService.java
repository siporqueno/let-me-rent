package ru.letmerent.core.services.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.letmerent.core.converters.CommentConverter;
import ru.letmerent.core.dto.comments.UserCommentDto;
import ru.letmerent.core.entity.comments.AboutUserComment;
import ru.letmerent.core.repositories.comments.UserCommentsRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCommentsService {
    private final UserCommentsRepository commentsRepository;
    private final CommentConverter converter;

    public void addNewComment(UserCommentDto commentDto) {
        commentsRepository.save(converter.fromDtoToAboutUserComment(commentDto));
    }

    public Collection<UserCommentDto> getCommentsAboutUser(Long userId) {
        Collection<AboutUserComment> comments = commentsRepository.findAllByAboutUserId(userId);
        return comments.stream().map(converter::fromAboutUserCommentToDto).collect(Collectors.toList());
    }

    public double getUserGrade(Long userId) {
        return commentsRepository.getAvgGrade(userId);
    }
}
