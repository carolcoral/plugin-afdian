package site.xindu.afdian.component;

import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.PluginContext;
import run.halo.app.theme.dialect.TemplateHeadProcessor;

@Component
@RequiredArgsConstructor
public class CustomHeadProcessor implements TemplateHeadProcessor {

    private final PropertyPlaceholderHelper
        PROPERTY_PLACEHOLDER_HELPER = new PropertyPlaceholderHelper("${", "}");
    private final PluginContext pluginContext;

    @Override
    public Mono<Void> process(ITemplateContext context, IModel model,
        IElementModelStructureHandler structureHandler) {
        return Mono.just(this.contactFormHtml()).doOnNext((html) -> {
            IModelFactory modelFactory = context.getModelFactory();
            model.add(modelFactory.createText(html));
        }).then();
    }

    private String contactFormHtml() {
        Properties properties = new Properties();
        properties.setProperty("version", this.pluginContext.getVersion());
        properties.setProperty("pluginStaticPath", "/plugins/plugin-afdian/assets/static");
        return this.PROPERTY_PLACEHOLDER_HELPER.replacePlaceholders("", properties);
    }
}
