package ru.letmerent.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.letmerent.core.entity.Picture;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findAllByInstrumentId(Long instrumentId);
    Picture findByName(String name);
}
