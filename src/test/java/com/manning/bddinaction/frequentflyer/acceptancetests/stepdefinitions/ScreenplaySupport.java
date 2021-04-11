package com.manning.bddinaction.frequentflyer.acceptancetests.stepdefinitions;

import com.google.common.eventbus.EventBus;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.PerformableLogger;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.core.eventbus.Broadcaster;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class ScreenplaySupport {

    @Before
    public void setTheStage() {
        System.setProperty("webdriver.chrome.silentOutput","true");
        OnStage.setTheStage(new OnlineCast());
    }

    @ParameterType(".*")
    public Actor actor(String actorName) {
        return OnStage.theActorCalled(actorName);
    }
}
