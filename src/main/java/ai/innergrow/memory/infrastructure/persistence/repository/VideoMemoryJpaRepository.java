package ai.innergrow.memory.infrastructure.persistence.repository;

import ai.innergrow.memory.infrastructure.persistence.entity.VideoMemoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VideoMemoryJpaRepository extends JpaRepository<VideoMemoryEntity, Long> {
    Optional<VideoMemoryEntity> findByEventId(Long eventId);
}
