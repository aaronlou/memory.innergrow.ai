package ai.innergrow.memory.infrastructure.video;

import ai.innergrow.memory.domain.asset.model.Asset;
import ai.innergrow.memory.domain.video.service.VideoSynthesisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class StubVideoSynthesisService implements VideoSynthesisService {

    @Override
    public String synthesize(String scriptContent, List<Asset> assets) {
        log.info("Starting video synthesis based on AI script. Material count: {}", assets.size());
        
        // 模拟合成耗时
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 模拟生成视频文件路径
        String videoId = UUID.randomUUID().toString();
        String resultUrl = "http://localhost:8080/uploads/memory_" + videoId + ".mp4";
        
        log.info("Video synthesis completed. Result: {}", resultUrl);
        return resultUrl;
    }
}
