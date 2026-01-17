package ai.innergrow.memory.interfaces.rest;

import ai.innergrow.memory.application.community.CommunityAppService;
import ai.innergrow.memory.domain.community.model.Community;
import ai.innergrow.memory.interfaces.rest.dto.CommunityCreateRequest;
import ai.innergrow.memory.interfaces.rest.dto.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityAppService communityAppService;

    @PostMapping
    public Result<Community> createCommunity(@Valid @RequestBody CommunityCreateRequest request) {
        Community community = communityAppService.createCommunity(
                request.getName(),
                request.getDescription(),
                request.getCreatorId()
        );
        return Result.success(community);
    }

    @GetMapping("/{id}")
    public Result<Community> getCommunity(@PathVariable Long id) {
        return Result.success(communityAppService.getCommunity(id));
    }
}
