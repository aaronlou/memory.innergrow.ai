package ai.innergrow.memory.domain.video.repository;

import ai.innergrow.memory.domain.video.model.VideoMemory;
import java.util.Optional;

public interface VideoMemoryRepository {
    VideoMemory save(VideoMemory videoMemory);
    Optional<VideoMemory> findByEventId(Long eventId);
}
