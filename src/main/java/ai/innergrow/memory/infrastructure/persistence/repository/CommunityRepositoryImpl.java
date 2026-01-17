package ai.innergrow.memory.infrastructure.persistence.repository;

import ai.innergrow.memory.domain.community.model.Community;
import ai.innergrow.memory.domain.community.repository.CommunityRepository;
import ai.innergrow.memory.infrastructure.persistence.entity.CommunityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepository {
    private final CommunityJpaRepository communityJpaRepository;

    @Override
    public Community save(Community community) {
        return toDomain(communityJpaRepository.save(toEntity(community)));
    }

    @Override
    public Optional<Community> findById(Long id) {
        return communityJpaRepository.findById(id).map(this::toDomain);
    }

    private CommunityEntity toEntity(Community domain) {
        return CommunityEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .creatorId(domain.getCreatorId())
                .build();
    }

    private Community toDomain(CommunityEntity entity) {
        return Community.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .creatorId(entity.getCreatorId())
                .build();
    }
}
