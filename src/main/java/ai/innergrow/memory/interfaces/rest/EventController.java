package ai.innergrow.memory.interfaces.rest;

import ai.innergrow.memory.application.event.EventAppService;
import ai.innergrow.memory.domain.event.model.Event;
import ai.innergrow.memory.interfaces.rest.dto.EventCreateRequest;
import ai.innergrow.memory.interfaces.rest.dto.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import ai.innergrow.memory.domain.event.model.EventRegistration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Event Management", description = "活动管理与报名相关接口")
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventAppService eventAppService;

    @Operation(summary = "活动报名", description = "用户报名参加指定的活动")
    @PostMapping("/{id}/signup")
    public Result<EventRegistration> signUp(@PathVariable Long id, @RequestParam Long userId) {
        return Result.success(eventAppService.signUp(id, userId));
    }

    @Operation(summary = "创建活动")
    @PostMapping
    public Result<Event> createEvent(@Valid @RequestBody EventCreateRequest request) {
        Event event = eventAppService.createEvent(
                request.getCommunityId(),
                request.getTitle(),
                request.getDescription(),
                request.getStartTime(),
                request.getEndTime(),
                request.getLocation()
        );
        return Result.success(event);
    }

    @Operation(summary = "获取活动详情")
    @GetMapping("/{id}")
    public Result<Event> getEvent(@PathVariable Long id) {
        return Result.success(eventAppService.getEvent(id));
    }

    @Operation(summary = "获取社群下的活动列表")
    @GetMapping("/community/{communityId}")
    public Result<List<Event>> getCommunityEvents(@PathVariable Long communityId) {
        return Result.success(eventAppService.getCommunityEvents(communityId));
    }
}
