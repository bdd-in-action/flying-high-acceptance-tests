package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.api;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;

public class BaseAPI {
    protected EnvironmentVariables environmentVariables;

    protected String endpointUrlFor(String path) {
        String apiBaseUrl = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("api.base.url");
        return apiBaseUrl + path;
    }
}
