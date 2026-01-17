package ai.innergrow.memory.domain.video.service;

import ai.innergrow.memory.domain.asset.model.Asset;
import ai.innergrow.memory.domain.event.model.Event;
import ai.innergrow.memory.domain.video.model.VideoScriptingModel;
import java.util.List;

public interface VideoGenerationService {
    /**
     * 根据活动信息和素材生成视频脚本和摘要 (统一模型)
     */
    VideoScriptingModel generateScript(Event event, List<Asset> assets);
}
