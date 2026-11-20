package com.yoterra.hippo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.HttpHeaders;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addHeaders(HttpHeaders headers) {
        headers.remove("cache-control");
        headers.remove("connection");
    }
}