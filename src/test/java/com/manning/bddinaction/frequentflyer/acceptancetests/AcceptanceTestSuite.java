package com.manning.bddinaction.frequentflyer.acceptancetests;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "classpath:features",
        tags="not @pending"
//tags="@journey-scenario"
//        tags="@current"
)
public class AcceptanceTestSuite {}