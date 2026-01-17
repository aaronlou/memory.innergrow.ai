package ai.innergrow.memory.application.event;

import ai.innergrow.memory.domain.event.model.Event;
import ai.innergrow.memory.domain.event.model.EventRegistration;
import ai.innergrow.memory.domain.event.model.Location;
import ai.innergrow.memory.domain.event.repository.EventRegistrationRepository;
import ai.innergrow.memory.domain.event.repository.EventRepository;
import ai.innergrow.memory.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventAppService {
    private final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final UserRepository userRepository;

    @Transactional
    public EventRegistration signUp(Long eventId, Long userId) {
        eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (eventRegistrationRepository.findByEventIdAndUserId(eventId, userId).isPresent()) {
            throw new RuntimeException("Already registered");
        }

        EventRegistration registration = EventRegistration.builder()
                .eventId(eventId)
                .userId(userId)
                .registrationTime(LocalDateTime.now())
                .build();
        return eventRegistrationRepository.save(registration);
    }

    @Transactional
    public Event createEvent(Long communityId, String title, String description, LocalDateTime startTime, LocalDateTime endTime, String location) {
        Event event = Event.builder()
                .communityId(communityId)
                .title(title)
                .description(description)
                .startTime(startTime)
                .endTime(endTime)
                .location(new Location(location))
                .build();
        return eventRepository.save(event);
    }

    public List<Event> getCommunityEvents(Long communityId) {
        return eventRepository.findByCommunityId(communityId);
    }

    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }
}
