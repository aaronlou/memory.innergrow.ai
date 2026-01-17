package ai.innergrow.memory.domain.community.repository;

import ai.innergrow.memory.domain.community.model.Community;
import java.util.Optional;

public interface CommunityRepository {
    Community save(Community community);
    Optional<Community> findById(Long id);
}
