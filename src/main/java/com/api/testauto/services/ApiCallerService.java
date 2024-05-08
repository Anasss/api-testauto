package com.api.testauto.services;

import com.api.testauto.utils.ApiProperties;
import com.api.testauto.utils.Request;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiCallerService {

    @Autowired
    private ApiProperties apiProperties;

    public Response performRequest(final Request request, final boolean useToken) {
        final RequestSpecification requestConfiguration = new RequestSpecBuilder()
                .setBaseUri(request.getEndpoint())
                .setConfig(RestAssured.config())
                .addQueryParams(request.getQueryParams())
                .build()
                .filter(new RequestLoggingFilter());

        if (useToken) {
            requestConfiguration.header("apiKey", apiProperties.getToken());
        }

        // Perform the GET request
        final Response response = RestAssured.given()
                .spec(requestConfiguration)
                .get();

        return response;
    }
}
