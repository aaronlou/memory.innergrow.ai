package ai.innergrow.memory.infrastructure.persistence.repository;

import ai.innergrow.memory.domain.event.model.EventRegistration;
import ai.innergrow.memory.domain.event.repository.EventRegistrationRepository;
import ai.innergrow.memory.infrastructure.persistence.entity.EventRegistrationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EventRegistrationRepositoryImpl implements EventRegistrationRepository {
    private final EventRegistrationJpaRepository eventRegistrationJpaRepository;

    @Override
    public EventRegistration save(EventRegistration registration) {
        return toDomain(eventRegistrationJpaRepository.save(toEntity(registration)));
    }

    @Override
    public Optional<EventRegistration> findByEventIdAndUserId(Long eventId, Long userId) {
        return eventRegistrationJpaRepository.findByEventIdAndUserId(eventId, userId).map(this::toDomain);
    }

    @Override
    public List<EventRegistration> findByEventId(Long eventId) {
        return eventRegistrationJpaRepository.findByEventId(eventId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private EventRegistrationEntity toEntity(EventRegistration domain) {
        return EventRegistrationEntity.builder()
                .id(domain.getId())
                .eventId(domain.getEventId())
                .userId(domain.getUserId())
                .registrationTime(domain.getRegistrationTime())
                .build();
    }

    private EventRegistration toDomain(EventRegistrationEntity entity) {
        return EventRegistration.builder()
                .id(entity.getId())
                .eventId(entity.getEventId())
                .userId(entity.getUserId())
                .registrationTime(entity.getRegistrationTime())
                .build();
    }
}
