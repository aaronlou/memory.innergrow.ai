package ai.innergrow.memory.interfaces.rest;

import ai.innergrow.memory.application.asset.AssetAppService;
import ai.innergrow.memory.domain.asset.model.Asset;
import ai.innergrow.memory.interfaces.rest.dto.AssetUploadRequest;
import ai.innergrow.memory.interfaces.rest.dto.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Tag(name = "Asset Management", description = "素材上传与管理相关接口")
@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {
    private final AssetAppService assetAppService;

    @Operation(summary = "上传素材", description = "支持图片和视频上传，仅限已报名活动的用户")
    @PostMapping(consumes = "multipart/form-data")
    public Result<Asset> uploadAsset(
            @Parameter(description = "活动ID") @RequestParam Long eventId,
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "素材类型") @RequestParam Asset.AssetType type,
            @RequestPart("file") MultipartFile file) throws IOException {
        
        Asset asset = assetAppService.uploadAsset(
                eventId,
                userId,
                file.getOriginalFilename(),
                file.getContentType(),
                file.getInputStream(),
                type
        );
        return Result.success(asset);
    }

    @Operation(summary = "获取活动素材列表")
    @GetMapping("/event/{eventId}")
    public Result<List<Asset>> getEventAssets(@PathVariable Long eventId) {
        return Result.success(assetAppService.getEventAssets(eventId));
    }
}
