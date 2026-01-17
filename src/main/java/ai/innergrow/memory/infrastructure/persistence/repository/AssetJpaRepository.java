package ai.innergrow.memory.infrastructure.persistence.repository;

import ai.innergrow.memory.infrastructure.persistence.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssetJpaRepository extends JpaRepository<AssetEntity, Long> {
    List<AssetEntity> findByEventId(Long eventId);
}
