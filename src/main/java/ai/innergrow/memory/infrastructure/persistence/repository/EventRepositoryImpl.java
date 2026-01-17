package ai.innergrow.memory.infrastructure.persistence.repository;

import ai.innergrow.memory.domain.event.model.Event;
import ai.innergrow.memory.domain.event.repository.EventRepository;
import ai.innergrow.memory.infrastructure.persistence.entity.EventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {
    private final EventJpaRepository eventJpaRepository;

    @Override
    public Event save(Event event) {
        return toDomain(eventJpaRepository.save(toEntity(event)));
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Event> findByCommunityId(Long communityId) {
        return eventJpaRepository.findByCommunityId(communityId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private EventEntity toEntity(Event domain) {
        return EventEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .location(domain.getLocation())
                .communityId(domain.getCommunityId())
                .build();
    }

    private Event toDomain(EventEntity entity) {
        return Event.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .location(entity.getLocation())
                .communityId(entity.getCommunityId())
                .build();
    }
}
