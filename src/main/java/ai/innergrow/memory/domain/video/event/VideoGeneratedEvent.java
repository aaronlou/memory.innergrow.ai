package ai.innergrow.memory.domain.video.event;

import ai.innergrow.memory.domain.video.model.VideoMemory;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class VideoGeneratedEvent extends ApplicationEvent {
    private final VideoMemory videoMemory;
    private final String status; // SUCCESS, FAILED

    public VideoGeneratedEvent(Object source, VideoMemory videoMemory, String status) {
        super(source);
        this.videoMemory = videoMemory;
        this.status = status;
    }
}
