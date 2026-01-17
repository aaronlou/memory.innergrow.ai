package ai.innergrow.memory.domain.asset.repository;

import ai.innergrow.memory.domain.asset.model.Asset;
import java.util.List;

public interface AssetRepository {
    Asset save(Asset asset);
    List<Asset> findByEventId(Long eventId);
}
