package koanruler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by chengyuan on 2017/10/18.
 */
//@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8080", "null")
//                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                .maxAge(3600)
//                .allowCredentials(true)
//                .allowedHeaders("authorization", "content-type");
//    }
//}