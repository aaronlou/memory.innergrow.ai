package ai.innergrow.memory.domain.asset.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asset {
    private Long id;
    private Long eventId;
    private Long userId;
    private String url;
    private AssetType type; // IMAGE, VIDEO

    public enum AssetType {
        IMAGE, VIDEO
    }
}
