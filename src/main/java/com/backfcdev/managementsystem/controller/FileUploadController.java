package com.backfcdev.managementsystem.controller;

import com.backfcdev.managementsystem.upload.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/files")
public class FileUploadController {

    private final StorageService storageService;


    @GetMapping
    ResponseEntity<Stream<String>> loadAll() {
        return ResponseEntity.ok(storageService.loadAll());
    }

    @GetMapping("/{filename:.+}")
    ResponseEntity<Resource> getFilename(@PathVariable String filename) throws IOException {
        Resource resource = storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(resource.getFile().toPath());
        return ResponseEntity.ok()
                .header("Content-Type", contentType)
                .body(resource);
    }

    @PostMapping("/upload")
    ResponseEntity<Map<String, String>> upload(@RequestParam MultipartFile file) {
        String route = storageService.store(file);
        Map<String, String> response = Collections.singletonMap("route", route);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    ResponseEntity<Void> deleteAll() {
        storageService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{filename}")
    ResponseEntity<Void> deleteFile(@PathVariable String filename) {
        storageService.deleteFile(filename);
        return ResponseEntity.noContent().build();
    }
}
