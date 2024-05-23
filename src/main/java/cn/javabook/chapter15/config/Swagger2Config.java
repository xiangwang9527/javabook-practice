package cn.javabook.chapter15.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("front").apiInfo(frontInfo())
                .select().paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }

    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("server").apiInfo(serverInfo())
                .select().paths(Predicates.and(PathSelectors.regex("/idempotent/.*")))
                .build();
    }

    private ApiInfo frontInfo() {
        return new ApiInfoBuilder()
                .title("前端API文档").description("本文档描述了前端微服务接口定义")
                .version("1.0")
                .contact(new Contact("javabook", "http://www.javabook.cn", "125043411@qq.com"))
                .build();
    }

    private ApiInfo serverInfo() {
        return new ApiInfoBuilder()
                .title("后端API文档").description("本文档描述了后端微服务接口定义")
                .version("1.0")
                .contact(new Contact("javabook", "http://www.javabook.cn", "125043411@qq.com"))
                .build();
    }
}
