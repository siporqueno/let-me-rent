package ru.letmerent.core.repositories.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.letmerent.core.entity.comments.AboutUserComment;

import java.util.Collection;

@Repository
public interface UserCommentsRepository extends JpaRepository<AboutUserComment, Long> {
    Collection<AboutUserComment> findAllByAboutUserId(Long userId);

    default double getAvgGrade(Long userId) {
        Collection<AboutUserComment> aboutUserComments = findAllByAboutUserId(userId);
        if (!aboutUserComments.isEmpty()) {
            aboutUserComments.stream()
                    .mapToInt(AboutUserComment::getGrade)
                    .average()
                    .orElse(0);
        }
        return 0;
    }
}
