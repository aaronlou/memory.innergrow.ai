package ai.innergrow.memory.infrastructure.persistence.repository;

import ai.innergrow.memory.infrastructure.persistence.entity.EventRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface EventRegistrationJpaRepository extends JpaRepository<EventRegistrationEntity, Long> {
    Optional<EventRegistrationEntity> findByEventIdAndUserId(Long eventId, Long userId);
    List<EventRegistrationEntity> findByEventId(Long eventId);
}
