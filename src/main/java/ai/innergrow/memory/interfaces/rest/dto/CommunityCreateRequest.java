package ai.innergrow.memory.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommunityCreateRequest {
    @NotBlank(message = "Community name is required")
    private String name;
    private String description;
    @NotBlank(message = "Creator ID is required")
    private String creatorId;
}
