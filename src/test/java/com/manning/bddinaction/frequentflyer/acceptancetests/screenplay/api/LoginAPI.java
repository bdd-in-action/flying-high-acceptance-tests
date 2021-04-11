package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.api;

import com.google.common.collect.ImmutableMap;
import io.restassured.RestAssured;

public class LoginAPI extends BaseAPI {

    private String adminUser = "admin@flyinghigh.com";
    private String adminPassword = "admin";

    public String getAuthToken() {

        String authToken = RestAssured.given()
                .contentType("application/json")
                .accept("*/*")
                .body(ImmutableMap.of("email", adminUser, "password", adminPassword))
                .post(endpointUrlFor("/auth/login"))
                .thenReturn()
                .body().jsonPath().getString("access_token");

        return authToken;
    }
}
