package webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadAbsolutePath = Paths.get("uploads/avatars").toFile().getAbsolutePath();
        registry.addResourceHandler("/uploads/avatars/**")
                .addResourceLocations("file:" + uploadAbsolutePath + "/");
    }
}
