package com.manning.bddinaction.frequentflyer.acceptancetests.pageobjects;

import com.manning.bddinaction.frequentflyer.acceptancetests.domain.MatchingFlight;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.TravelClass;
import net.serenitybdd.core.steps.UIInteractionSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class MatchingFlightsList extends UIInteractionSteps {

    private final static By MATCHING_FLIGHTS = By.cssSelector(".flight-container .card");
    private final static By DEPARTURE = By.cssSelector(".departure");
    private final static By DESTINATION = By.cssSelector(".destination");
    private final static By TRAVEL_CLASS = By.cssSelector(".travel-class");

    public List<MatchingFlight> matchingFlights() {
        return $$(MATCHING_FLIGHTS).stream()
                .map(element -> new MatchingFlight(
                        textOf(DEPARTURE),
                        textOf(DESTINATION),
                        TravelClass.withLabel(textOf(TRAVEL_CLASS))
                ))
                .collect(Collectors.toList());
    }
}
