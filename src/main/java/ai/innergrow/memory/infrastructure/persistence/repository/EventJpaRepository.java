package ai.innergrow.memory.infrastructure.persistence.repository;

import ai.innergrow.memory.infrastructure.persistence.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByCommunityId(Long communityId);
}
