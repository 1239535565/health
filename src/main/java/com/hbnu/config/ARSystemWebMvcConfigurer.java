package com.hbnu.config;

import com.hbnu.intercepter.LoginTokenInterception;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class ARSystemWebMvcConfigurer implements WebMvcConfigurer {

    /*
        配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有请求
        registry.addInterceptor(loginTokenInterception()).addPathPatterns("/**");
    }

    /*
        配置资源处理映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    /*
        设置跨域
        addMapping，设置可以被跨域的路径
        allowedOrigins，域名的白名单
        allowedMethods，请求的方式，GET,POST,DELETE,PUT
        allowedHeaders，允许所有请求header访问，可以自定义设置任意请求头信息
        maxAge，这个是给复杂请求预检用的，设置预检多久失效
    */
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("*").allowedHeaders("*").maxAge(3600);
    }*/

    /*
        登录验证拦截器
     */
    @Bean
    public LoginTokenInterception loginTokenInterception(){
        return new LoginTokenInterception();
    }
}
