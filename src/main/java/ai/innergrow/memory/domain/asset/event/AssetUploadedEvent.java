package ai.innergrow.memory.domain.asset.event;

import ai.innergrow.memory.domain.asset.model.Asset;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 领域事件：素材上传完成
 */
@Getter
public class AssetUploadedEvent extends ApplicationEvent {
    private final Asset asset;

    public AssetUploadedEvent(Object source, Asset asset) {
        super(source);
        this.asset = asset;
    }
}
