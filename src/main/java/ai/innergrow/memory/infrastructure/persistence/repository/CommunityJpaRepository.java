package ai.innergrow.memory.infrastructure.persistence.repository;

import ai.innergrow.memory.infrastructure.persistence.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityJpaRepository extends JpaRepository<CommunityEntity, Long> {
}
