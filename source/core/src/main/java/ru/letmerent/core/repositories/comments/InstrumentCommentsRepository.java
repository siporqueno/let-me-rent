package ru.letmerent.core.repositories.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.letmerent.core.entity.comments.AboutInstrumentComment;

import java.util.Collection;

@Repository
public interface InstrumentCommentsRepository extends JpaRepository<AboutInstrumentComment, Long> {
    Collection<AboutInstrumentComment> findAllByInstrumentId(Long instrumentId);

    default double getAvgGrade(Long instrumentId) {
        Collection<AboutInstrumentComment> aboutInstrumentComments = findAllByInstrumentId(instrumentId);
        if (!aboutInstrumentComments.isEmpty()) {
            return aboutInstrumentComments.stream()
                    .filter(c -> c.getGrade() != null)
                    .mapToInt(AboutInstrumentComment::getGrade)
                    .average()
                    .orElse(0.0);
        }
        return 0;
    }
}
