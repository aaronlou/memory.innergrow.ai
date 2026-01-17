package ai.innergrow.memory.domain.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistration {
    private Long id;
    private Long eventId;
    private Long userId;
    private LocalDateTime registrationTime;
}
