package cn.javabook;

import cn.javabook.chapter15.interceptor.IdempotentInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 应用启动入口
 *
 */
@ComponentScan(basePackages = "cn.javabook")
@SpringBootApplication(scanBasePackages = { "cn.javabook.chapter02.*" })
public class JavaBookPractice extends WebMvcConfigurerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(JavaBookPractice.class);

    /**
     * 跨域
     *
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 接口幂等性拦截器
        registry.addInterceptor(apiIdempotentInterceptor());
        super.addInterceptors(registry);
    }

    @Bean
    public IdempotentInterceptor apiIdempotentInterceptor() {
        return new IdempotentInterceptor();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        logger.warn("starting JavaBookPractice...");
        SpringApplication application = new SpringApplication(JavaBookPractice.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        long end = System.currentTimeMillis();
        logger.warn("started JavaBookPractice in {} seconds.", (end - start) / 1000);
    }
}
