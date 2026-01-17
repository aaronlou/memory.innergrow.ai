package ai.innergrow.memory.application.asset;

import ai.innergrow.memory.domain.asset.event.AssetUploadedEvent;
import ai.innergrow.memory.domain.asset.model.Asset;
import ai.innergrow.memory.domain.asset.repository.AssetRepository;
import ai.innergrow.memory.domain.common.StorageService;
import ai.innergrow.memory.domain.event.repository.EventRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetAppService {
    private final AssetRepository assetRepository;
    private final StorageService storageService;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Asset uploadAsset(Long eventId, Long userId, String fileName, String contentType, InputStream inputStream, Asset.AssetType type) {
        // 核心业务规则校验：只有报名参加活动的用户才能上传素材
        eventRegistrationRepository.findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new RuntimeException("Only registered participants can upload assets for this event"));

        String url = storageService.store(inputStream, fileName, contentType);
        
        Asset asset = Asset.builder()
                .eventId(eventId)
                .userId(userId)
                .url(url)
                .type(type)
                .build();
        
        Asset saved = assetRepository.save(asset);
        
        // 发布领域事件
        eventPublisher.publishEvent(new AssetUploadedEvent(this, saved));
        
        return saved;
    }

    public List<Asset> getEventAssets(Long eventId) {
        return assetRepository.findByEventId(eventId);
    }
}
