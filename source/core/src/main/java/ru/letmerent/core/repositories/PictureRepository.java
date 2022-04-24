package ru.letmerent.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.letmerent.core.entity.Picture;

import java.util.Collection;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    Collection<Picture> findAllByInstrumentId(Long instrumentId);
}
