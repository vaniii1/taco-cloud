package ihromovyi.tacocloud.config;

import ihromovyi.tacocloud.model.Ingredient;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, Ingredient.Type.class,
                source -> Ingredient.Type.valueOf(source.toUpperCase()));
    }
}
