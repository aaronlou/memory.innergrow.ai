package ai.innergrow.memory.domain.video.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoMemory {
    private Long id;
    private Long eventId;
    private String videoUrl;
    private String title;
    private String aiSummary;
    private LocalDateTime createdAt;
    private Status status;

    public enum Status {
        GENERATING, COMPLETED, FAILED
    }
}
