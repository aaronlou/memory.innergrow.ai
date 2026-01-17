package ai.innergrow.memory.infrastructure.persistence.repository;

import ai.innergrow.memory.domain.video.model.VideoMemory;
import ai.innergrow.memory.domain.video.repository.VideoMemoryRepository;
import ai.innergrow.memory.infrastructure.persistence.entity.VideoMemoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VideoMemoryRepositoryImpl implements VideoMemoryRepository {
    private final VideoMemoryJpaRepository videoMemoryJpaRepository;

    @Override
    public VideoMemory save(VideoMemory videoMemory) {
        return toDomain(videoMemoryJpaRepository.save(toEntity(videoMemory)));
    }

    @Override
    public Optional<VideoMemory> findByEventId(Long eventId) {
        return videoMemoryJpaRepository.findByEventId(eventId).map(this::toDomain);
    }

    private VideoMemoryEntity toEntity(VideoMemory domain) {
        return VideoMemoryEntity.builder()
                .id(domain.getId())
                .eventId(domain.getEventId())
                .videoUrl(domain.getVideoUrl())
                .title(domain.getTitle())
                .aiSummary(domain.getAiSummary())
                .createdAt(domain.getCreatedAt())
                .status(domain.getStatus())
                .build();
    }

    private VideoMemory toDomain(VideoMemoryEntity entity) {
        return VideoMemory.builder()
                .id(entity.getId())
                .eventId(entity.getEventId())
                .videoUrl(entity.getVideoUrl())
                .title(entity.getTitle())
                .aiSummary(entity.getAiSummary())
                .createdAt(entity.getCreatedAt())
                .status(entity.getStatus())
                .build();
    }
}
