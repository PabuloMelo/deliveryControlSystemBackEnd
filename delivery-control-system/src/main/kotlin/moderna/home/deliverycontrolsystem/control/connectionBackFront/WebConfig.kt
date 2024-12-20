package moderna.home.deliverycontrolsystem.control.connectionBackFront

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class  WebConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
       registry.addMapping("/**")
           .allowedOriginPatterns("*")
           .allowedMethods("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS")
           .allowedHeaders("*")
           .allowCredentials(true)
    }
}