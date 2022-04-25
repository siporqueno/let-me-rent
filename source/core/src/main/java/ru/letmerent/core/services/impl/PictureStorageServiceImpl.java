package ru.letmerent.core.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.Picture;
import ru.letmerent.core.repositories.PictureRepository;
import ru.letmerent.core.services.PictureStorageService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class PictureStorageServiceImpl implements PictureStorageService {
    private final Path root;
    private final PictureRepository pictureRepository;
    
    public PictureStorageServiceImpl(PictureRepository pictureRepository,
                                     @Value("${variables.picture-storage-root:picture_storage}") String path
    ) {
        this.pictureRepository = pictureRepository;
        
        root = Paths.get(path);
    }
    
    @Override
    public void save(MultipartFile file, Instrument instrument) {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (nonNull(originalFilename)) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            
            String newName = UUID.randomUUID() + extension;
            
            Files.copy(file.getInputStream(), this.root.resolve(newName));
            pictureRepository.save(new Picture(newName, instrument));
        } catch (IOException e) {
            log.error("Could not save picture: {}", e.getClass());
            throw new RuntimeException("Could not save picture " + " " + file.getName());
        }
    }
    
    @Override
    public Resource load(String pictureName) {
        try {
            Path picture = root.resolve(pictureName);
            Resource resource = new UrlResource(picture.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the picture or picture does not exists!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    
    @Override
    public Resource load(Long pictureId) {
        Optional<Picture> oPicture = pictureRepository.findById(pictureId);
        if (oPicture.isPresent()) {
            return load(oPicture.get().getName());
        } else {
            throw new RuntimeException("Could not read the picture or picture does not exists!");
        }
    }
    
    @Override
    public void deletePictures(Long instrumentId, List<Long> pictureIds) {
        Collection<Picture> allByInstrumentId = pictureRepository.findAllByInstrumentId(instrumentId);
        if (isNull(pictureIds) || pictureIds.isEmpty()) {
            allByInstrumentId.forEach(pic -> {
                try {
                    Files.deleteIfExists(root.resolve(pic.getName()));
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            });
            pictureRepository.deleteAll(allByInstrumentId);
        } else {
            List<Picture> picturesToDelete = allByInstrumentId
                .stream()
                .filter(pic -> pictureIds.contains(pic.getId()))
                .collect(Collectors.toList());
            picturesToDelete.forEach(pic -> {
                try {
                    Files.deleteIfExists(root.resolve(pic.getName()));
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            });
            pictureRepository.deleteAll(picturesToDelete);
        }
    }
    
    @Override
    public MediaType getMediaType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        switch (extension) {
            case "gif":
                return MediaType.IMAGE_GIF;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.IMAGE_PNG;
        }
    }
    
    @Override
    public List<Picture> findAllPictureByInstrumentId(Long instrumentId) {
        return pictureRepository.findAllByInstrumentId(instrumentId);
    }
}
