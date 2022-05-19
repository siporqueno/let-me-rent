package ru.letmerent.core.services;

import java.io.File;

public interface MinioService {

    void uploadFile(File file, String shortName);

    void removeFile(String shortName);

    byte[] downloadFile(String shortName);
}
