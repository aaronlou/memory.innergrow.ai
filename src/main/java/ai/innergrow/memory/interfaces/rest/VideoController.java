package ai.innergrow.memory.interfaces.rest;

import ai.innergrow.memory.application.video.VideoAppService;
import ai.innergrow.memory.domain.video.model.VideoMemory;
import ai.innergrow.memory.interfaces.rest.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Video Generation", description = "AI 视频生成与管理相关接口")
@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoAppService videoAppService;

    @Operation(summary = "开启 AI 视频生成任务", description = "触发异步 AI 编排流程，生成活动回顾脚本和模拟视频")
    @PostMapping("/generate/{eventId}")
    public Result<VideoMemory> startGeneration(@PathVariable Long eventId) {
        return Result.success(videoAppService.startGeneration(eventId));
    }

    @Operation(summary = "获取视频生成结果")
    @GetMapping("/event/{eventId}")
    public Result<VideoMemory> getVideoMemory(@PathVariable Long eventId) {
        return Result.success(videoAppService.getVideoMemory(eventId));
    }
}
