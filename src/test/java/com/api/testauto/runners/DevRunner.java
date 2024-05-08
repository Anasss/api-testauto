package com.api.testauto.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"target/test-classes/features"},
        glue = {"com.api.testauto.steps"},
        plugin = {"html:target/cukes/cukes.html", "json:target/cucumber-Dev-report.json", "junit:target/cucumber-Dev-report.xml",
                "pretty"},
        tags = "@test")
public class DevRunner {
}