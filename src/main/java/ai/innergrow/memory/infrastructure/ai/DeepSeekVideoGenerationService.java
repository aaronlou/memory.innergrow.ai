package ai.innergrow.memory.infrastructure.ai;

import ai.innergrow.memory.domain.asset.model.Asset;
import ai.innergrow.memory.domain.event.model.Event;
import ai.innergrow.memory.domain.video.model.VideoScriptingModel;
import ai.innergrow.memory.domain.video.service.VideoGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeepSeekVideoGenerationService implements VideoGenerationService {

    private final ChatClient chatClient;
    
    // 适配器专用的内部格式，用于匹配特定供应商的 JSON 输出
    public record AIOutput(String title, String aiSummary, String scriptContent, List<AIScene> scenes) {
        public record AIScene(int sequence, String description, String narration, String suggestedMaterialType) {}
    }

    private final BeanOutputConverter<AIOutput> outputConverter = new BeanOutputConverter<>(AIOutput.class);

    public DeepSeekVideoGenerationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public VideoScriptingModel generateScript(Event event, List<Asset> assets) {
        String assetInfo = assets.stream()
                .map(a -> "- 素材类型: " + a.getType() + ", URL: " + a.getUrl())
                .collect(Collectors.joining("\n"));

        String prompt = """
                你是一个专业的活动回顾视频导演。
                请根据以下活动信息和素材列表，为该活动策划一个精彩的回顾视频方案。
                
                活动背景:
                名称: %s
                描述: %s
                
                可用素材清单:
                %s
                
                要求:
                1. 标题(title)要富有感染力。
                2. AI总结(aiSummary)应是一段感性的文字，升华活动主题。
                3. 脚本内容(scriptContent)描述整体镜头逻辑。
                4. scenes 数组包含具体的场景细节。
                
                {format}
                """.formatted(event.getTitle(), event.getDescription(), assetInfo);

        log.info("Requesting structured AI script for event: {}", event.getTitle());
        
        try {
            AIOutput response = chatClient.prompt()
                    .user(u -> u.text(prompt).param("format", outputConverter.getFormat()))
                    .call()
                    .entity(AIOutput.class);
            
            // ACL 映射: AI特定模型 -> 领域模型
            return VideoScriptingModel.builder()
                    .title(response.title())
                    .aiSummary(response.aiSummary())
                    .scriptContent(response.scriptContent())
                    .scenes(response.scenes().stream()
                            .map(s -> VideoScriptingModel.Scene.builder()
                                    .sequence(s.sequence())
                                    .description(s.description())
                                    .narration(s.narration())
                                    .suggestedMaterialType(s.suggestedMaterialType())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            log.error("Failed to generate AI script, falling back to default", e);
            return VideoScriptingModel.builder()
                    .title(event.getTitle() + " - 活动回顾")
                    .aiSummary("见证美好时刻")
                    .scriptContent("简单剪辑")
                    .scenes(List.of())
                    .build();
        }
    }
}
