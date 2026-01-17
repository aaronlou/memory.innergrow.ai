package ai.innergrow.memory.application.video;

import ai.innergrow.memory.domain.video.model.VideoMemory;
import ai.innergrow.memory.domain.video.repository.VideoMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import ai.innergrow.memory.application.asset.AssetAppService;
import ai.innergrow.memory.application.event.EventAppService;
import ai.innergrow.memory.domain.asset.model.Asset;
import ai.innergrow.memory.domain.event.model.Event;
import ai.innergrow.memory.domain.video.model.VideoScriptingModel;
import ai.innergrow.memory.domain.video.service.VideoGenerationService;
import ai.innergrow.memory.domain.video.service.VideoSynthesisService;
import org.springframework.scheduling.annotation.Async;
import java.util.List;

import ai.innergrow.memory.domain.video.event.VideoGeneratedEvent;
import org.springframework.context.ApplicationEventPublisher;

@Service
@RequiredArgsConstructor
public class VideoAppService {
    private final VideoMemoryRepository videoMemoryRepository;
    private final EventAppService eventAppService;
    private final AssetAppService assetAppService;
    private final VideoGenerationService videoGenerationService;
    private final VideoSynthesisService videoSynthesisService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public VideoMemory startGeneration(Long eventId) {
        Event event = eventAppService.getEvent(eventId);
        
        VideoMemory videoMemory = VideoMemory.builder()
                .eventId(eventId)
                .title(event.getTitle() + " - 生成中")
                .status(VideoMemory.Status.GENERATING)
                .createdAt(LocalDateTime.now())
                .build();
        
        VideoMemory saved = videoMemoryRepository.save(videoMemory);
        
        // 异步执行 AI 生成逻辑
        processVideoGeneration(saved.getId(), eventId);
        
        return saved;
    }

    @Async
    public void processVideoGeneration(Long memoryId, Long eventId) {
        try {
            Event event = eventAppService.getEvent(eventId);
            List<Asset> assets = assetAppService.getEventAssets(eventId);
            
            // 1. 调用 AI 生成剧本 (现在使用的是防腐层后的统一模型)
            VideoScriptingModel script = videoGenerationService.generateScript(event, assets);
            
            // 2. 调用视频合成引擎（真实合成）
            String finalVideoUrl = videoSynthesisService.synthesize(script.scriptContent(), assets);
            
            VideoMemory memory = videoMemoryRepository.findByEventId(eventId)
                    .orElseThrow(() -> new RuntimeException("Memory record lost"));
            
            VideoMemory updated = VideoMemory.builder()
                    .id(memory.getId())
                    .eventId(eventId)
                    .title(script.title())
                    .aiSummary(script.aiSummary())
                    .videoUrl(finalVideoUrl)
                    .status(VideoMemory.Status.COMPLETED)
                    .createdAt(memory.getCreatedAt())
                    .build();
            
            VideoMemory saved = videoMemoryRepository.save(updated);
            
            // 发布领域事件：生成成功
            eventPublisher.publishEvent(new VideoGeneratedEvent(this, saved, "SUCCESS"));
        } catch (Exception e) {
            videoMemoryRepository.findByEventId(eventId).ifPresent(m -> {
                VideoMemory failed = VideoMemory.builder()
                        .id(m.getId())
                        .eventId(eventId)
                        .status(VideoMemory.Status.FAILED)
                        .createdAt(m.getCreatedAt())
                        .build();
                VideoMemory savedFailed = videoMemoryRepository.save(failed);
                
                // 发布领域事件：生成失败
                eventPublisher.publishEvent(new VideoGeneratedEvent(this, savedFailed, "FAILED"));
            });
        }
    }

    public VideoMemory getVideoMemory(Long eventId) {
        return videoMemoryRepository.findByEventId(eventId).orElseThrow(() -> new RuntimeException("Memory not found"));
    }
}
