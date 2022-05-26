package ru.letmerent.core.services.impl;

import lombok.RequiredArgsConstructor;
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
import ru.letmerent.core.services.MinioService;
import ru.letmerent.core.services.PictureStorageService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class PictureStorageServiceImpl implements PictureStorageService {

    @Value("${variables.picture-storage-root:picture_storage}")
    private String storagePath;

    private Path root;

    private final PictureRepository pictureRepository;

    private final MinioService minioService;

    @PostConstruct
    public void init() {
        this.root = Paths.get(storagePath);
    }

    @Override
    public void save(MultipartFile file, Instrument instrument) {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
            Path temp = Paths.get(storagePath, "temp");
            if (!Files.exists(temp)) {
                Files.createDirectory(temp);
            }
    
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (nonNull(originalFilename)) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String newName = UUID.randomUUID() + extension;
    
            Path filePath = temp.resolve(newName);
            Files.copy(file.getInputStream(), filePath);
            File f = new File(String.valueOf(filePath));

            minioService.uploadFile(f);
            pictureRepository.save(new Picture(newName, instrument));
        } catch (IOException e) {
            log.error("Could not save picture: {}", e.getClass());
            throw new RuntimeException("Could not save picture " + " " + file.getName());
        }
    }

    @Override
    public Resource load(String pictureName) {
        try {
            byte[] bytes = minioService.downloadFile(pictureName);
            File file = new File(String.valueOf(this.root.resolve(pictureName)));
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
            } catch (Exception e) {
                log.error("Can't load picture from minio: {}", e.getMessage());
            }
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
            allByInstrumentId.forEach(pic -> minioService.removeFile(pic.getName()));
            pictureRepository.deleteAll(allByInstrumentId);
        } else {
            List<Picture> picturesToDelete = allByInstrumentId
                    .stream()
                    .filter(pic -> pictureIds.contains(pic.getId()))
                    .collect(Collectors.toList());
            picturesToDelete.forEach(pic -> minioService.removeFile(pic.getName()));
            pictureRepository.deleteAll(picturesToDelete);
        }
    }
    
    @Override
    public void deletePicture(Long instrumentId, Long pictureId) {
        deletePictures(instrumentId, Collections.singletonList(pictureId));
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
