package pres.linebot.linebot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .ignoredParameterTypes(Errors.class)
            .select()
            .apis(RequestHandlerSelectors.basePackage("pres.linebot.linebot"))
            .paths(PathSelectors.any())
            .build()
            .groupName("linebot");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("LINE BOT TEST")
            .description("LINE BOT TEST API")
            .version("v1")
            .build();
    }

}
