package ai.innergrow.memory.infrastructure.storage;

import ai.innergrow.memory.domain.common.StorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${storage.local.path:uploads}")
    private String uploadPath;

    @Value("${storage.local.base-url:http://localhost:8080/uploads/}")
    private String baseUrl;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(InputStream inputStream, String fileName, String contentType) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String storedFileName = UUID.randomUUID().toString() + extension;
            Path targetLocation = Paths.get(uploadPath).resolve(storedFileName);
            
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            return baseUrl + storedFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}
