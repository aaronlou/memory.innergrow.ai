package ai.innergrow.memory.application.video;

import ai.innergrow.memory.application.asset.AssetAppService;
import ai.innergrow.memory.application.event.EventAppService;
import ai.innergrow.memory.domain.asset.model.Asset;
import ai.innergrow.memory.domain.event.model.Event;
import ai.innergrow.memory.domain.video.event.VideoGeneratedEvent;
import ai.innergrow.memory.domain.video.model.VideoMemory;
import ai.innergrow.memory.domain.video.model.VideoScriptingModel;
import ai.innergrow.memory.domain.video.repository.VideoMemoryRepository;
import ai.innergrow.memory.domain.video.service.VideoGenerationService;
import ai.innergrow.memory.domain.video.service.VideoSynthesisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoAppServiceTest {

    @Mock
    private VideoMemoryRepository videoMemoryRepository;
    @Mock
    private EventAppService eventAppService;
    @Mock
    private AssetAppService assetAppService;
    @Mock
    private VideoGenerationService videoGenerationService;
    @Mock
    private VideoSynthesisService videoSynthesisService;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private VideoAppService videoAppService;

    @Test
    void processVideoGeneration_Success() {
        // Arrange
        Long eventId = 1L;
        Long memoryId = 10L;
        Event mockEvent = Event.builder().id(eventId).title("Test Event").build();
        VideoMemory mockMemory = VideoMemory.builder().id(memoryId).eventId(eventId).build();
        VideoScriptingModel mockScript = VideoScriptingModel.builder()
                .title("Title")
                .aiSummary("Summary")
                .scriptContent("Content")
                .scenes(List.of())
                .build();

        when(eventAppService.getEvent(eventId)).thenReturn(mockEvent);
        when(assetAppService.getEventAssets(eventId)).thenReturn(Collections.emptyList());
        when(videoGenerationService.generateScript(any(), any())).thenReturn(mockScript);
        when(videoSynthesisService.synthesize(anyString(), anyList())).thenReturn("http://fake.url");
        when(videoMemoryRepository.findByEventId(eventId)).thenReturn(Optional.of(mockMemory));
        when(videoMemoryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        videoAppService.processVideoGeneration(memoryId, eventId);

        // Assert
        verify(videoMemoryRepository, atLeastOnce()).save(any());
        verify(eventPublisher).publishEvent(any(VideoGeneratedEvent.class));
    }
}
