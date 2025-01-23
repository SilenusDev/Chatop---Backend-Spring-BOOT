package com.openclassrooms.api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageUploadService {

    private static final String UPLOAD_DIR = "src/upload/";
    private static final String IMAGE_PATH_PREFIX = "/api/images/";

    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        Path filePath = Paths.get(UPLOAD_DIR + uniqueFilename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        // Retourner le chemin complet de l'image
        return IMAGE_PATH_PREFIX + uniqueFilename;
    }
}

