package ai.innergrow.memory.infrastructure.persistence.entity;

import ai.innergrow.memory.domain.video.model.VideoMemory;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "video_memories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoMemoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long eventId;

    private String videoUrl;
    private String title;

    @Column(length = 2000)
    private String aiSummary;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VideoMemory.Status status;
}
