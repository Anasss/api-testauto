Feature: Test Time series endpoint - Lets you query the API for daily historical rates between two dates of your choice.
  With a maximum time frame of 365 days.
  Ex: GET https://api.apilayer.com/fixer/timeseries?start_date=2024-01-01&end_date=2024-01-01

  @test
  Scenario Outline: Get valid historical rates between two valid dates
    Given User makes a GET request to Fixer API endpoint with <start_date> and <end_date>
    Then The response matches the schema <valid_rates_schema> and the status code should be <status_code>
    And The response should contain historical rates between <start_date> and <end_date>

    Examples:
      | start_date | end_date   | valid_rates_schema    | status_code |
      | 2024-02-09 | 2024-12-30 | ValidRatesSchema.json | OK          |
      | 2019-01-01 | 2019-12-31 | ValidRatesSchema.json | OK          |
      | 2020-05-01 | 2021-02-22 | ValidRatesSchema.json | OK          |

  Scenario Outline: Get historical rates with invalid dates
    Given User makes a GET request to Fixer API endpoint with <start_date> and <end_date>
    Then The response matches the schema <invalid_dates_schema> and the status code should be <status_code>

    Examples:
      | start_date | end_date   | invalid_dates_schema     | status_code |
      | 2024-02-30 | 2024-12-30 | ResponseErrorSchema.json | BAD_REQUEST |
      | 2022-01-01 | 2022-13-31 | ResponseErrorSchema.json | BAD_REQUEST |

  Scenario Outline: Get historical rates with start date after end date
    Given User makes a GET request to Fixer API endpoint with <start_date> and <end_date>
    Then The response matches the schema <invalid_range_schema> and the status code should be <status_code>

    Examples:
      | start_date | end_date   | invalid_range_schema     | status_code |
      | 2024-12-30 | 2024-02-09 | ResponseErrorSchema.json | BAD_REQUEST |

  Scenario Outline: Get historical rates with invalid endpoint : Missing dates
    Given User makes a GET request to Fixer API endpoint without dates
    Then The response matches the schema <not_found_schema> and the status code should be <status_code>

    Examples:
      | not_found_schema         | status_code |
      | ResponseErrorSchema.json | NOT_FOUND   |

  Scenario Outline: Get historical rates with too many requests (Reaching the API rate limit)
    Given User makes <number_of_attempts> number of GET requests to Fixer API endpoint with <start_date> and <end_date>
    Then The response matches the schema <too_many_requests_schema> and the status code should be <status_code>

    Examples:
      | start_date | end_date   | too_many_requests_schema | status_code       | number_of_attempts |
      | 2024-02-09 | 2024-12-30 | ResponseErrorSchema.json | TOO_MANY_REQUESTS | 100                |

  Scenario Outline: Get historical rates without authorization
    Given User makes a GET request to Fixer API endpoint without a token and with <start_date> and <end_date>
    Then The response matches the schema <unauthorized_schema> and the status code should be <status_code>

    Examples:
      | start_date | end_date   | unauthorized_schema             | status_code  |
      | 2024-02-09 | 2024-08-24 | UnauthorizedResponseSchema.json | UNAUTHORIZED |