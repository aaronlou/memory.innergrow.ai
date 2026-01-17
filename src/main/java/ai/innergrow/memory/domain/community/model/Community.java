package ai.innergrow.memory.domain.community.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Community {
    private Long id;
    private String name;
    private String description;
    private String creatorId;
}
