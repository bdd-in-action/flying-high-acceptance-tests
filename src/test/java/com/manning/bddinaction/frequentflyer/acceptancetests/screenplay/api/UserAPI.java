package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.api;

import com.google.common.collect.ImmutableMap;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.Traveller;
import io.restassured.RestAssured;

public class UserAPI extends BaseAPI {

    private String adminUser = "admin@flyinghigh.com";
    private String adminPassword = "admin";

    public String createNewUser(String accessToken, Traveller traveller) {

        String userId = RestAssured.given()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .accept("*/*")
                .body(traveller)
                .post(endpointUrlFor("/users"))
                .body().jsonPath().getString("userId");

        return userId;
    }
}
