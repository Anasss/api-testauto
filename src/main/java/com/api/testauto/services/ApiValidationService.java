package com.api.testauto.services;

import com.api.testauto.utils.FileReader;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * Service class for API response validation.
 */
@Service
public class ApiValidationService {

    private static final String UNEXPECTED_STATUS_CODE_MSG = "The current status code %s is not matching the expected one %s.";

    @Autowired
    private FileReader fileReader;

    /**
     * Validates the REST response.
     * Checks status code and validates the response body using the given JSON schema.
     *
     * @param response
     * @param jsonSchemaPath
     * @param expectedStatus
     */
    public void validateResponse(final Response response, final String jsonSchemaPath, final HttpStatus expectedStatus) {
        validateResponseCode(response, expectedStatus);
        String jsonValue;
        try {
            jsonValue = fileReader.readFileFromResources(jsonSchemaPath);
        } catch (final IOException e) {
            throw new IllegalStateException(String.format("Error reading JSON schema from path: %s", jsonSchemaPath), e);
        }

        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonValue));
    }

    /**
     * Checks whether the response code is as expected.
     *
     * @param response
     * @param expectedStatus
     */
    private void validateResponseCode(final Response response, final HttpStatus expectedStatus) {
        final int currentStatusCode = response.getStatusCode();
        final int expectedStatusCode = expectedStatus.value();
        Assert.isTrue(currentStatusCode == expectedStatusCode,
                String.format(UNEXPECTED_STATUS_CODE_MSG, currentStatusCode, expectedStatusCode));
    }
}