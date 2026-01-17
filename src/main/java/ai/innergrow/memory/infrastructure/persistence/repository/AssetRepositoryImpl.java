package ai.innergrow.memory.infrastructure.persistence.repository;

import ai.innergrow.memory.domain.asset.model.Asset;
import ai.innergrow.memory.domain.asset.repository.AssetRepository;
import ai.innergrow.memory.infrastructure.persistence.entity.AssetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AssetRepositoryImpl implements AssetRepository {
    private final AssetJpaRepository assetJpaRepository;

    @Override
    public Asset save(Asset asset) {
        return toDomain(assetJpaRepository.save(toEntity(asset)));
    }

    @Override
    public List<Asset> findByEventId(Long eventId) {
        return assetJpaRepository.findByEventId(eventId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private AssetEntity toEntity(Asset domain) {
        return AssetEntity.builder()
                .id(domain.getId())
                .eventId(domain.getEventId())
                .userId(domain.getUserId())
                .url(domain.getUrl())
                .type(domain.getType())
                .build();
    }

    private Asset toDomain(AssetEntity entity) {
        return Asset.builder()
                .id(entity.getId())
                .eventId(entity.getEventId())
                .userId(entity.getUserId())
                .url(entity.getUrl())
                .type(entity.getType())
                .build();
    }
}
