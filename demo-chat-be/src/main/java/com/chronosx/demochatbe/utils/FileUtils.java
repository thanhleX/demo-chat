package com.chronosx.demochatbe.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileUtils {

    public static String FOLDER_UPLOADS = "uploads";

    public static String FOLDER_AVATARS = "avatars";

    @Value("${backend.url}")
    public static String BACK_END_URL;

    public static String storeFile(MultipartFile file, String folderName) throws IOException {
        // validate
        if (file.isEmpty()) throw new IllegalArgumentException("File is empty");
        if (folderName.isEmpty()) throw new IllegalArgumentException("Folder name is empty");

        // store
        String newFileName = UUID.randomUUID().toString() + getFileExtension(file);
        Path path = Path.of(FOLDER_UPLOADS, folderName);

        if (path.toFile().exists()) path.toFile().mkdirs();

        file.transferTo(path.resolve(newFileName));

        return newFileName;
    }

    private static Object getFileExtension(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));
    }

    public static void deleteFile(String fileNamePath) throws IOException {
        Path path = Path.of(FOLDER_UPLOADS, fileNamePath);
        Files.deleteIfExists(path);
    }

    public static String getAvatarUrl(String fileName) {
        return BACK_END_URL + "/images/" + FOLDER_AVATARS + "/" + fileName;
    }
}
