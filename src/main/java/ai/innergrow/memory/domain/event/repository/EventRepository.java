package ai.innergrow.memory.domain.event.repository;

import ai.innergrow.memory.domain.event.model.Event;
import java.util.Optional;
import java.util.List;

public interface EventRepository {
    Event save(Event event);
    Optional<Event> findById(Long id);
    List<Event> findByCommunityId(Long communityId);
}
