package com.api.testauto.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "fixer.api")
public class ApiProperties {

    @Value("${fixer.api.token}")
    private String token;

    @Value("${fixer.api.endpoint}")
    private String endpoint;
}
