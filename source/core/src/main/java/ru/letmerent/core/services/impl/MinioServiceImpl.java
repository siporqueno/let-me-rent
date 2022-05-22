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

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioServiceImpl implements MinioService {

    @Value("${minio.bucket}")
    private String bucket;

    private final MinioClient minioClient;

    @Override
    public void uploadFile(File file) {
        String name = file.getName();
        try {
            minioClient.uploadObject(
                UploadObjectArgs.builder()
                    .bucket(bucket)
                    .object(name)
                    .filename(file.getAbsolutePath())
                    .build());
        } catch (Exception e) {
            log.error("Загрузка {} прошла не успешно", name);
        }finally {
            if(file.exists()){
                file.delete();
            }
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
    public byte[] downloadFile(String shortName) {
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(shortName)
                    .build();
            return minioClient.getObject(args).readAllBytes();
        } catch (Exception e) {
            log.error("Выгрузка {} прошла не успешно", shortName);
            return null;
        }
    }
}
