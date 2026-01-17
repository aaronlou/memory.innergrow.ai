package ai.innergrow.memory.application.notification;

import ai.innergrow.memory.domain.video.event.VideoGeneratedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @EventListener
    public void handleVideoGeneratedEvent(VideoGeneratedEvent event) {
        log.info("Notification Service: Receiving event for video memory ID: {}", event.getVideoMemory().getId());
        
        if ("SUCCESS".equals(event.getStatus())) {
            log.info("Sending success notification: Your video '{}' is ready! URL: {}", 
                event.getVideoMemory().getTitle(), event.getVideoMemory().getVideoUrl());
        } else {
            log.error("Sending failure notification: Sorry, generation for event ID {} failed.", 
                event.getVideoMemory().getEventId());
        }
    }
}
