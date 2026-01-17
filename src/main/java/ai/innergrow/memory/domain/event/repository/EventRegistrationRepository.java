package ai.innergrow.memory.domain.event.repository;

import ai.innergrow.memory.domain.event.model.EventRegistration;
import java.util.Optional;
import java.util.List;

public interface EventRegistrationRepository {
    EventRegistration save(EventRegistration registration);
    Optional<EventRegistration> findByEventIdAndUserId(Long eventId, Long userId);
    List<EventRegistration> findByEventId(Long eventId);
}
