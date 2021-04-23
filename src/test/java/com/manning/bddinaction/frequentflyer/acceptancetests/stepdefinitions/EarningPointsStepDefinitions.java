package com.manning.bddinaction.frequentflyer.acceptancetests.stepdefinitions;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Optional;

public class EarningPointsStepDefinitions {

    @Given("{word} is a new Frequent Flyer Member")
    public void isANewFrequentFlyerMember(String name) {
    }

    @ParameterType("\\d*")
    public Optional<Integer> points(String points) {
        return ((points == null) || points.isEmpty()) ? Optional.empty() : Optional.of(Integer.parseInt(points));
    }

    @When("he has earned between {points} and {points} points")
    public void heHasEarnedBetween(Optional<Integer> minPoints, Optional<Integer> maxPoints) {
    }

    @Then("his status should become {word}")
    public void hisStatusShouldBecomeStatusLevel(String statusLevel) {
    }

    @When("points are calculated for a flight to or from {}")
    public void pointsAreCalculatedForAFlightToOrFromCity(String cityName) {
    }

    @Then("the city should be considered to be part of the {word} region")
    public void theCityShouldBeConsideredToBePartOfTheRegion(String regionName) {
    }
}
