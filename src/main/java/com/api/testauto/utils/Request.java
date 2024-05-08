package com.api.testauto.utils;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class Request {
    private String endpoint;
    private Method method;
    private ContentType contentType;
    private Map<String, String> queryParams;
}
