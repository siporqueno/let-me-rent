package ru.letmerent.core.services.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.letmerent.core.converters.CommentConverter;
import ru.letmerent.core.dto.comments.InstrumentCommentDto;
import ru.letmerent.core.repositories.comments.InstrumentCommentsRepository;

@Service
@RequiredArgsConstructor
public class InstrumentCommentService {

    private final InstrumentCommentsRepository commentsRepository;
    private final CommentConverter converter;

    public void addNewComment(InstrumentCommentDto commentDto) {

    }
}
