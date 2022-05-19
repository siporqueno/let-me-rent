package ru.letmerent.core.services.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.letmerent.core.services.MinioService;

import java.io.File;
import java.io.FileOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioServiceImpl implements MinioService {

    @Value("${minio.bucket}")
    private String bucket;

    private final MinioClient minioClient;

    @Override
    public void uploadFile(File file, String shortName) {
        String fullFileName = "/tmp/" + shortName;
        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucket)
                            .object(shortName)
                            .filename(fullFileName)
                            .build());
        } catch (Exception e) {
            log.error("Загрузка {} прошла не успешно", shortName);
        }
    }

    @Override
    public void removeFile(String shortName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(shortName)
                    .build());
        } catch (Exception e) {
            log.error("Удаление {} прошло не успешно", shortName);
        }
    }

    @Override
    public File downloadFile(String shortName) {
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(shortName)
                    .build();
            byte[] bytes = minioClient.getObject(args).readAllBytes();
            File file = new File(shortName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            log.error("Выгрузка {} прошла не успешно", shortName);
            return null;
        }
    }
}
