package ru.letmerent.core.services.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.letmerent.core.converters.CommentConverter;
import ru.letmerent.core.dto.comments.InstrumentCommentDto;
import ru.letmerent.core.repositories.comments.InstrumentCommentsRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstrumentCommentService {

    private final InstrumentCommentsRepository commentsRepository;
    private final CommentConverter converter;

    public void addNewComment(InstrumentCommentDto commentDto) {
        commentsRepository.save(converter.fromDtoToAboutInstrumentComment(commentDto));
    }

    public Collection<InstrumentCommentDto> getCommentsAboutInstrument(Long instrumentId) {
        return commentsRepository.findAllByInstrumentId(instrumentId).stream()
                .map(converter::fromAboutInstrumentCommentToDto)
                .collect(Collectors.toList());
    }

    public double getInstrumentGrade(Long instrumentId) {
        return commentsRepository.getAvgGrade(instrumentId);
    }
}
