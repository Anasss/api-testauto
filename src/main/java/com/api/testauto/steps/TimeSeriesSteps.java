package com.api.testauto.steps;

import com.api.testauto.services.ApiCallerService;
import com.api.testauto.services.ApiValidationService;
import com.api.testauto.utils.ApiProperties;
import com.api.testauto.utils.FileReader;
import com.api.testauto.utils.Request;
import com.api.testauto.worlds.RestResponseWorld;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class TimeSeriesSteps {

    final String JSON_SCHEMA_FOLDER = "schema/";

    @Autowired
    private ApiCallerService apiCallerService;

    @Autowired
    private ApiValidationService apiValidationService;

    @Autowired
    private RestResponseWorld restResponseWorld;

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private FileReader fileReader;

    @Given("User makes a GET request to Fixer API endpoint with {} and {}")
    public void performGetTimeSeriesRequest(final String startDate, final String endDate) {
        Request request = Request.builder().endpoint(apiProperties.getEndpoint())
                .method(Method.GET)
                .contentType(ContentType.JSON)
                .queryParams(Map.ofEntries(Map.entry("start_date", startDate), Map.entry("end_date", endDate)))
                .build();

        // Perform the GET request
        Response response = apiCallerService.performRequest(request, true);

        // Save the response in the RestResponseWorld
        restResponseWorld.setResponse(response);
    }

    @Then("The response matches the schema {} and the status code should be {}")
    public void validateResponse(final String schema, final HttpStatus expectedStatus) {
        // Get the response from the RestResponseWorld
        final Response response = restResponseWorld.getResponse();
        // Validate the response schema and the status code
        apiValidationService.validateResponse(response, JSON_SCHEMA_FOLDER + schema, expectedStatus);
    }

    @And("The response should contain historical rates between {} and {}")
    public void validateRatesBetweenTwoDates(final String startDate, final String endDate) {
        // Get the response from the RestResponseWorld
        final Response response = restResponseWorld.getResponse();

        // Parse the JSON response
        final JsonPath jsonPath = new JsonPath(response.asString());

        // Extract the dates from the response
        final Map<String, Object> rates = jsonPath.get("rates");

        // Parse the start and end dates to LocalDate objects
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        // Validate the dates
        for (final String date : rates.keySet()) {
            try {
                LocalDate current = LocalDate.parse(date);
                if (current.isBefore(start) || current.isAfter(end)) {
                    throw new AssertionError("Date out of range in response: " + date);
                }
            } catch (final DateTimeParseException e) {
                throw new AssertionError("Invalid date in response: " + date, e);
            }
        }
    }

    @Given("User makes a GET request to Fixer API endpoint without dates")
    public void performGetTimeSeriesInvalidEndpoint() {
        Request request = Request.builder().endpoint(apiProperties.getEndpoint())
                .method(Method.GET)
                .contentType(ContentType.JSON)
                .build();

        // Perform the GET request
        Response response = apiCallerService.performRequest(request, true);

        // Save the response in the RestResponseWorld
        restResponseWorld.setResponse(response);
    }

    @Given("User makes {} number of GET requests to Fixer API endpoint with {} and {}")
    public void performMultipleGetTimeSeriesRequest(final int numberOfRequests, final String startDate, final String endDate) {
        for (int i = 0; i < numberOfRequests; i++) {
            Request request = Request.builder().endpoint(apiProperties.getEndpoint())
                    .method(Method.GET)
                    .contentType(ContentType.JSON)
                    .queryParams(Map.ofEntries(Map.entry("start_date", startDate), Map.entry("end_date", endDate)))
                    .build();

            // Perform the GET request
            Response response = apiCallerService.performRequest(request, true);

            // Save the response in the RestResponseWorld
            restResponseWorld.setResponse(response);
        }
    }

    @Given("User makes a GET request to Fixer API endpoint without a token and with {} and {}")
    public void performGetTimeSeriesMissingToken(final String startDate, final String endDate) {
        Request request = Request.builder().endpoint(apiProperties.getEndpoint())
                .method(Method.GET)
                .contentType(ContentType.JSON)
                .queryParams(Map.ofEntries(Map.entry("start_date", startDate), Map.entry("end_date", endDate)))
                .build();

        // Perform the GET request
        Response response = apiCallerService.performRequest(request, false);

        // Save the response in the RestResponseWorld
        restResponseWorld.setResponse(response);
    }

}