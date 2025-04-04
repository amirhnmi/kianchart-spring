package com.kianchart.kianchart.core.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class UploadFile {

    public static String upload(String uploadDir,MultipartFile file) throws IOException {
        Path directory = Paths.get(uploadDir);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        String fileName = UUID.randomUUID().toString().split("-")[0] + "." + file.getOriginalFilename();
        Path filePath = directory.resolve(fileName);

        Files.write(filePath, file.getBytes());
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
