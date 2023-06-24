package com.backfcdev.managementsystem.upload;

import com.backfcdev.managementsystem.exception.MediaFileNotFoundException;
import com.backfcdev.managementsystem.exception.StorageException;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService  {

    private final Path storageLocation = Paths.get("uploads");


    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(storageLocation);
        } catch (IOException ex) {
            throw new StorageException("Failed to create file store: " + storageLocation);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalFilename);
        if (file.isEmpty()) {
            throw new StorageException("Cannot store an empty file " + originalFilename);
        }
        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, Paths.get(storageLocation.toUri()).resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new StorageException("Failed to save the file " + originalFilename);
        }
        return filename;
    }

    @Override
    public Stream<String> loadAll() {
        try {
            return Files.walk(Paths.get(storageLocation.toUri()), 1)
                    .filter(path -> !path.equals(Paths.get(storageLocation.toUri())))
                    .map(path -> path.getFileName().toString());
        } catch (IOException e) {
            throw new StorageException("Could not load the files!");
        }
    }


    @Override
    public Resource loadAsResource(String filename) {
        Path filePath = Paths.get(storageLocation.toUri()).resolve(filename);
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException ex) {
            throw new MediaFileNotFoundException("The file has not been found: " + filename);
        }

        if (!resource.exists() || !resource.isReadable()) {
            throw new MediaFileNotFoundException("The file has not been found: " + filename);
        }

        return resource;
    }

    @Override
    public void deleteAll() {
        File directory = storageLocation.toFile();
        File[] files = directory.listFiles();
        if (files != null) Arrays.stream(files)
                .filter(File::isFile)
                .filter(file -> !file.delete())
                .forEach(file -> {
                    throw new StorageException("Could not delete file: " + file.getName());
                });
    }

    @Override
    public void deleteFile(String filename) {
        Path filePath = Paths.get(storageLocation.toUri()).resolve(filename);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new StorageException("Could not delete file: " + filePath);
        }
    }
}

