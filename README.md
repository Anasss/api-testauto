![Github Actions](https://github.com/Anasss/api-testauto/actions/workflows/maven.yml/badge.svg)
# API Test Automation

This repository contains an API testing automation suite using Spring Boot, Cucumber and Rest Assured.
The current feature is to test the Fixer time series API, which provides historical exchange rates.
See: https://apilayer.com/marketplace/fixer-api

This suite can test other APIs by adding new feature files and step definitions.

## Testing approach

#### JSON Schema validation: 
- We generate a JSON schema from our HTTP request expected reply.
- We check that the API returns the expected response code and matches the JSON Schema.
- We can also perform additional functional checks if needed.

## Project Structure

- `src/main/java/com/api/testauto/steps`: Contains the Cucumber step definitions.
- `src/main/java/com/api/testauto/services`: Contains the services used in the step definitions.
- `src/main/java/com/api/testauto/utils`: Contains utility classes.
- `src/main/java/com/api/testauto/worlds`: Contains the world objects that share state between steps.

## Setting up the project

To set up the project, you can execute the following commands:

```shell
git clone https://github.com/Anasss/api-testauto.git
cd api-testauto
mvn install
```

You can launch the tests by executing the following command:
```shell
mvn test
```

## Cucumber tag annotation
In Cucumber, tags are used to filter scenarios that you want to execute. You can add tags to your scenarios in your feature files using the `@test` For example:

```gherkin
@test
Scenario Outline: Get valid historical rates between two valid dates
Given User makes a GET request to the Fixer API endpoint with <start_date> and <end_date>
Then The response matches the schema <valid_rates_schema> and the status code should be <status_code>
```

### Reference Documentation
For further reference, please consider the following sections:

* [Cucumber](https://cucumber.io/docs)
* [Rest Assured](https://rest-assured.io/)
* [Spring Boot](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/)
* [Maven](https://maven.apache.org/guides/index.html)
