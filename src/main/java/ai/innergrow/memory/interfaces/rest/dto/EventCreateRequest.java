package ai.innergrow.memory.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventCreateRequest {
    @NotNull(message = "Community ID is required")
    private Long communityId;
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
}
