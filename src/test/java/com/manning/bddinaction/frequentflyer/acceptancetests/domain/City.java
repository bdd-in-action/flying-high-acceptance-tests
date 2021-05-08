package com.manning.bddinaction.frequentflyer.acceptancetests.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record City(
        @JsonProperty("name") String name,
        @JsonProperty("code") String code,
        @JsonProperty("region") String region) {}
