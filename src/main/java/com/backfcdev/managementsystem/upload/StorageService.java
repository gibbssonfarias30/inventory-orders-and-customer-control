package com.backfcdev.managementsystem.upload;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    String store(MultipartFile file);

    Stream<String> loadAll();

    Resource loadAsResource(String filename);

    void deleteAll();

    void deleteFile(String filename);
}
