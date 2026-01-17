package ai.innergrow.memory.domain.common;

import java.io.InputStream;

public interface StorageService {
    /**
     * 存储文件并返回访问 URL
     */
    String store(InputStream inputStream, String fileName, String contentType);
}
