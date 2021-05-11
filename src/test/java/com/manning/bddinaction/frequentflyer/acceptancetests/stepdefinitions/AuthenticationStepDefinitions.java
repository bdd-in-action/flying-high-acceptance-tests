package com.manning.bddinaction.frequentflyer.acceptancetests.stepdefinitions;

import com.manning.bddinaction.frequentflyer.acceptancetests.domain.UserLevel;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.Traveller;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.TravellerPersona;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.login.Login;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.registration.RegisterAsAFrequentFlyer;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.ux.Acknowledge;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class AuthenticationStepDefinitions {

    @Given("{actor} is a new Frequent Flyer Member")
    public void aNewFrequentFlyerMember(Actor traveller) {
        traveller.attemptsTo(
                RegisterAsAFrequentFlyer.viaTheAPI().withMemberDetailsFrom(
                        TravellerPersona.withName(traveller.getName()).withAUniqueEmailAddress()
                )
        );
    }

    @Given("{actor} is a Frequent Flyer Member with status {statusLevel}")
    public void aNewFrequentFlyerMember(Actor traveller, UserLevel statusLevel) {
        traveller.attemptsTo(
                RegisterAsAFrequentFlyer.viaTheAPI().withMemberDetailsFrom(
                        TravellerPersona.withName(traveller.getName())
                                .withAUniqueEmailAddress()
                                .withLevel(statusLevel)
                )
        );
    }

    @Given("{actor} has logged onto the Frequent Flyer application as a new member")
    public void loginAsANewMember(Actor member) {
        Traveller traveller = TravellerPersona.withName(member.getName()).withAUniqueEmailAddress();
        member.attemptsTo(
                RegisterAsAFrequentFlyer.viaTheAPI().withMemberDetailsFrom(traveller),
                Login.as(traveller)
        );
    }

    @When("{actor} has logged onto the Frequent Flyer application")
    public void loginAs(Actor member) {
        Traveller currentUser = member.recall("CURRENT_USER");
        member.attemptsTo(
                Login.as(currentUser),
                Acknowledge.successMessageOf("Logged in as " + currentUser.email())
        );
    }

}
