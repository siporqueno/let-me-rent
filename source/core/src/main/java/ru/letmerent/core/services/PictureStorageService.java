package ru.letmerent.core.services;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.Picture;

import java.util.List;

public interface PictureStorageService {
    void save(MultipartFile picture, Instrument instrument);
    
    List<String> loadInstrumentPicturesName(Instrument instrument);
    
    List<Picture> findAllPictureByInstrumentId(Long instrumentId);
    
    Resource load(String pictureName);
    
    void deletePictures(Instrument instrument, List<Long> pictureIds);
    
    MediaType getMediaType(String fileName);
    
}
