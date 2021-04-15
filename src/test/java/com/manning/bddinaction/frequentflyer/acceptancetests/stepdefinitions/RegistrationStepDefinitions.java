package com.manning.bddinaction.frequentflyer.acceptancetests.stepdefinitions;

import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.Traveller;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.TravellerPersona;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.login.Login;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.myaccount.MyAccount;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.navigation.Navigate;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.registration.RegisterAsAFrequentFlyer;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.registration.RegistrationForm;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.*;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.type.Type;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.hamcrest.Matcher;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.hasItem;

public class RegistrationStepDefinitions {

    /**
     * Here we store all the information about the new Frequent Flyer member
     */
    Traveller newMember;

    @ParameterType(".*")
    public Traveller traveller(String travellerName) {
        return TravellerPersona.withName(travellerName);
    }

    @Given("{traveller} does not have a Frequent Flyer account")
    public void notAFrequentFlyerMember(Traveller traveller) {
        // This new member account has an email address that has never been used before
        newMember = traveller.withAUniqueEmailAddress();
        theActorCalled(traveller.getFirstName()).describedAs("A new Frequent Flyer");
    }

    @Given("{traveller} is a Frequent Flyer member with the following details:")
    public void existingFrequentFlyer(Traveller traveller, Map<String, String> frequentFlyerDetails) {

    }

    @When("{actor} registers as a Frequent Flyer member")
    public void registersAsAFrequentFlyerMember(Actor traveller) {
        traveller.attemptsTo(
                RegisterAsAFrequentFlyer.withMemberDetailsFrom(newMember)
        );
    }

    @Then("{actor} should be able to log on to the Frequent Flyer application")
    public void shouldBeAbleToLoginAs(Actor member) {
        member.attemptsTo(
                Login.as(newMember),
                WaitUntil.the(Login.SUCCESS_MESSAGE, isVisible()),
                Ensure.that(Login.SUCCESS_MESSAGE).hasTextContent("Logged in as " + newMember.getEmail())
        );
    }


    @Then("{actor} logs on to the Frequent Flyer application")
    public void loginAs(Actor actor) {
        actor.attemptsTo(
                Login.as(newMember),
                WaitUntil.the(Login.SUCCESS_MESSAGE, isVisible()),
                Click.on(Login.SUCCESS_MESSAGE),
                WaitUntil.the(Login.SUCCESS_MESSAGE, isNotVisible())
        );
    }

    @Then("{actor} should have a Frequent Flyer account with:")
    public void aNewFrequentFlyerMemberAccountShouldBeCreatedWith(Actor actor, Map<String, String> expectedStatus) {
        String expectedPoints = expectedStatus.get("Points");
        String expectedStatusLevel = expectedStatus.get("Status Level");
        actor.attemptsTo(
                Navigate.toMyAccount(),
                Ensure.that(MyAccount.POINT_BALANCE).text().isEqualTo(expectedPoints),
                Ensure.that(MyAccount.STATUS_LEVEL).text().isEqualTo(expectedStatusLevel)
        );
    }

    @When("{actor} wants to register a new Frequent Flyer account")
    public void triesToRegisterANewAccount(Actor actor) {
        actor.attemptsTo(Navigate.toTheRegistrationPage());
    }

    /**
     * This example shows a more advanced use of Consequence objects to do soft assertions
     */
    @Then("the following emails should not be considered valid:")
    public void invalidEmails(List<Map<String, String>> invalidEmails) {
        Ensure.enableSoftAssertions();
        theActorInTheSpotlight().attemptsTo(
                Iterate.over(invalidEmails).forEach(
                        (actor, row) ->
                                actor.attemptsTo(
                                        Clear.field(RegistrationForm.EMAIL),
                                        Type.theValue(row.get("Email")).into(RegistrationForm.EMAIL),
                                        Click.on(RegistrationForm.REGISTER),
                                        WaitUntil.the(RegistrationForm.FORM_ERROR_MESSAGES, isVisible()),
                                        Ensure.thatTheAnswersTo(RegistrationForm.errorMessages()).contains(row.get("Message"))
                                )
                )
        );
        Ensure.reportSoftAssertions();
    }

    @Then("the following information should be mandatory to register:")
    public void mandatoryFields(List<Map<String, String>> mandatoryFields) {
        Ensure.enableSoftAssertions();
        theActorInTheSpotlight().attemptsTo(
                Iterate.over(mandatoryFields).forEach(
                        (actor, row) -> actor.attemptsTo(
                                RegisterAsAFrequentFlyer.withMemberDetailsFrom(newMember.withEmptyValueFor(row.get("Field"))),
                                WaitUntil.the(RegistrationForm.FORM_ERROR_MESSAGES, isVisible()),
                                Ensure.thatTheAnswersTo(RegistrationForm.errorMessages()).contains(row.get("Error Message If Missing"))
                        )
                )
        );
        Ensure.reportSoftAssertions();
    }

    @Given("{actor} has registered as a Frequent Flyer member")
    public void hasRegisteredAsAFrequentFlyerMember(Actor actor) {
        newMember = TravellerPersona.withName(actor.getName()).withAUniqueEmailAddress();
        actor.attemptsTo(
                RegisterAsAFrequentFlyer.viaTheAPI().withMemberDetailsFrom(newMember)
        );
    }

    @When("{actor} tries to register with the same email")
    public void triesToRegisterWithTheSameEmail(Actor actor) {
        String existingEmail = newMember.getEmail();
        Traveller travellerWithADuplicatedEmail = TravellerPersona.withName(actor.getName()).withEmail(existingEmail);
        actor.attemptsTo(
                RegisterAsAFrequentFlyer.withMemberDetailsFrom(travellerWithADuplicatedEmail)
        );
    }

    @When("{actor} tries to register with an email of {string}")
    public void triesToRegisterWithAUsernameOf(Actor actor, String email) {
        actor.attemptsTo(
                // Override the default traveller properties with a different email
                RegisterAsAFrequentFlyer.withMemberDetailsFrom(
                        newMember.withEmail(email)
                )
        );
    }

    @Then("{actor} should be told {string}")
    public void shouldBeTold(Actor actor, String errorMessage) {
        actor.should(
                seeThat(RegistrationForm.errorMessages(), hasItem(errorMessage))
        );
    }

    @Then("{actor} should be notified that {string}")
    public void shouldBeNotified(Actor actor, String errorMessage) {
        actor.attemptsTo(
                WaitUntil.the(RegistrationForm.ERROR_NOTIFICATION_MESSAGE, isVisible()),
                Ensure.that(RegistrationForm.ERROR_NOTIFICATION_MESSAGE).hasTextContent(errorMessage)
        );
    }

    @When("{actor} tries to register without approving the terms and conditions")
    public void triesToRegisterWithoutApprovingTheTermsAndConditions(Actor actor) {
        actor.attemptsTo(
                RegisterAsAFrequentFlyer.withMemberDetailsFrom(
                        newMember.whoDoesNotApproveTheTermsAndConditions()
                )
        );
    }

    @But("she doesn't provide a value for {word}")
    public void sheDoesnTProvideAValueForField(String field) {
            theActorInTheSpotlight().attemptsTo(
                    RegisterAsAFrequentFlyer.withMemberDetailsFrom(
                            newMember.withEmptyValueFor(field)
                    )
            );

    }
}
