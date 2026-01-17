package ai.innergrow.memory.interfaces.rest.dto;

import ai.innergrow.memory.domain.asset.model.Asset;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssetUploadRequest {
    @NotNull(message = "Event ID is required")
    private Long eventId;
    @NotNull(message = "User ID is required")
    private Long userId;
    @NotBlank(message = "URL is required")
    private String url;
    @NotNull(message = "Asset type is required")
    private Asset.AssetType type;
}
