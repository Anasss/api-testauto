package com.api.testauto.worlds;

import io.cucumber.spring.CucumberTestContext;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@Scope(value = CucumberTestContext.SCOPE_CUCUMBER_GLUE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RestResponseWorld {
    Response response;
}
