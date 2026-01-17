package ai.innergrow.memory.application.community;

import ai.innergrow.memory.domain.community.model.Community;
import ai.innergrow.memory.domain.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityAppService {
    private final CommunityRepository communityRepository;

    @Transactional
    public Community createCommunity(String name, String description, String creatorId) {
        Community community = Community.builder()
                .name(name)
                .description(description)
                .creatorId(creatorId)
                .build();
        return communityRepository.save(community);
    }

    public Community getCommunity(Long id) {
        return communityRepository.findById(id).orElseThrow(() -> new RuntimeException("Community not found"));
    }
}
