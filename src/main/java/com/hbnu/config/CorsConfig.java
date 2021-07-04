package com.hbnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 解决vue跨域问题
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(CorsProperties corsProperties) {
        //1,添加CORS配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //2,允许的域
        corsConfiguration.setAllowedOriginPatterns(corsProperties.getOrigin());
        //2,是否允许前端发送携带cookie信息的跨域请求
        corsConfiguration.setAllowCredentials(corsProperties.isCredentials());
        /*
            2，允许的头信息
            CORS请求时，XMLHttpRequest对象的getResponseHeader()方法只能拿到6个基本字段，
            如果想拿到其他字段就必须在Access-Control-Expose-Headers中指定
        */
        corsConfiguration.setAllowedHeaders(corsProperties.getHeaders());
        //2，允许的请求方式
        corsConfiguration.setAllowedMethods(corsProperties.getMethods());
        //2,允许的时间
        corsConfiguration.setMaxAge(corsProperties.getMaxAge());

        //3,添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsProperties.getPath(), corsConfiguration);
        return new CorsFilter(source);
    }
}
