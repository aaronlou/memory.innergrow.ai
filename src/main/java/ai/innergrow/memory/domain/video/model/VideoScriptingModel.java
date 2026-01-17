package ai.innergrow.memory.domain.video.model;

import lombok.Builder;
import java.util.List;

/**
 * 统一的 AI 剧本领域模型（防腐层核心）
 * 无论外部 AI 返回什么格式，最终都必须转换为这个模型
 */
@Builder
public record VideoScriptingModel(
    String title,
    String aiSummary,
    String scriptContent,
    List<Scene> scenes
) {
    @Builder
    public record Scene(
        int sequence,
        String description,
        String narration,
        String suggestedMaterialType // 例如：PHOTO, VIDEO
    ) {}
}
