package ai.innergrow.memory.domain.video.service;

import ai.innergrow.memory.domain.asset.model.Asset;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VideoSynthesisService {
    /**
     * 根据 AI 生成的脚本和素材列表合成真实的视频文件
     * @param scriptContent AI 生成的视频脚本
     * @param assets 选中的素材列表
     * @return 合成后的视频 URL 的异步结果
     */
    String synthesize(String scriptContent, List<Asset> assets);
}
