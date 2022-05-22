package ru.letmerent.core.services;

import java.io.File;

public interface MinioService {

    void uploadFile(File file);

    void removeFile(String shortName);

    byte[] downloadFile(String shortName);
}
