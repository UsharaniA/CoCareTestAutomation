package testrunner;


import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/features/CreateNewEncounter.feature",
    glue = "stepdefinitions",
    plugin = {
        "pretty",
        "json:target/cucumber.json",
        "usage:target/cucumber-usage.json",
        "html:target/cucumber-reports.html",
        "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"
    }
  
)


public class RunCucumberTest {
	 
}