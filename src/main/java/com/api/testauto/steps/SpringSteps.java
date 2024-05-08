package com.api.testauto.steps;


import com.api.testauto.TestAutoApplication;

import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Cucumber steps class used for integration of Spring framework into cucumber.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = TestAutoApplication.class)
public class SpringSteps {

    /**
     * Any cucumber's hook method is required to integrate Spring with Cucumber.
     */
    @Before(order = 0)
    public void initializeSpringContext() {
    }
}