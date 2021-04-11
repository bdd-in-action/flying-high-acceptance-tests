package com.manning.bddinaction.frequentflyer.acceptancetests.stepdefinitions;

import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.Traveller;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.TravellerPersona;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.login.Login;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.myaccount.MyAccount;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.navigation.Navigate;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.registration.RegisterAsAFrequentFlyer;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.registration.RegistrationForm;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Consequence;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.type.Type;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.contains;
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
        OnStage.theActorCalled(traveller.getFirstName()).describedAs("A new Frequent Flyer");
    }

    @Given("{traveller} is a Frequent Flyer member with the following details:")
    public void existingFrequentFlyer(Traveller traveller, Map<String, String> frequentFlyerDetails) {

    }

    @When("{actor} registers as a Frequent Flyer member")
    public void registersAsAFrequentFlyerMember(Actor actor) {
        actor.attemptsTo(
                RegisterAsAFrequentFlyer.withMemberDetailsFrom(newMember)
        );
    }

    /**
     * When this step is a precondition, we create the account using the API
     *
     * @param actor
     */
    @Given("{actor} has registered as a Frequent Flyer member")
    public void hasRegistersAsAFrequentFlyerMember(Actor actor) {
        newMember = TravellerPersona.withName(actor.getName()).withAUniqueEmailAddress();
        actor.attemptsTo(
                RegisterAsAFrequentFlyer.viaTheAPI().withMemberDetailsFrom(newMember)
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

    @Then("{actor} should be able to log on to the Frequent Flyer application")
    public void shouldBeAbleToLoginAs(Actor actor) {
        actor.attemptsTo(
                Login.as(newMember),
                WaitUntil.the(Login.SUCCESS_MESSAGE, isVisible()),
                Ensure.that(Login.SUCCESS_MESSAGE).hasTextContent("Logged in as " + newMember.getEmail())
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
        theActorInTheSpotlight().should(
                seeTheCorrectErrorMessageForEachEmailIn(invalidEmails)
        );
    }

    /**
     * Convert each row in the Cucumber table into a Consequence object.
     * These are evaluated independently and behave like soft assertions.
     */
    private List<Consequence<?>> seeTheCorrectErrorMessageForEachEmailIn(List<Map<String, String>> invalidEmails) {

        List<Consequence<?>> errorMessageChecks = new ArrayList<>();

        invalidEmails.forEach(
                invalidEmail -> errorMessageChecks.add(
                        seeThat(
                                RegistrationForm.errorMessages(), hasItem(invalidEmail.get("Message"))
                        ).after(
                                Clear.field(RegistrationForm.EMAIL),
                                Type.theValue(invalidEmail.get("Email")).into(RegistrationForm.EMAIL),
                                Click.on(RegistrationForm.REGISTER),
                                WaitUntil.the(RegistrationForm.FORM_ERROR_MESSAGES, isVisible())
                        )
                )
        );
        return errorMessageChecks;
    }

    @Then("the following information should be mandatory to register:")
    public void mandatoryFields(List<Map<String, String>> mandatoryFields) {
        theActorInTheSpotlight().should(
                seeThatTheFollowingFieldsAreMandatory(mandatoryFields)
        );
    }

    private List<Consequence<?>> seeThatTheFollowingFieldsAreMandatory(List<Map<String, String>> mandatoryFields) {

        List<Consequence<?>> mandatoryFieldsChecks = new ArrayList<>();
        mandatoryFields.forEach(
                mandatoryField -> mandatoryFieldsChecks.add(
                        InteractiveConsequence.attemptTo(
                                RegisterAsAFrequentFlyer.withMemberDetailsFrom(newMember.withEmptyValueFor(mandatoryField.get("Field"))),
                                WaitUntil.the(RegistrationForm.FORM_ERROR_MESSAGES, isVisible())
                        ).thenCheckThat(
                                RegistrationForm.errorMessages(), hasItem(mandatoryField.get("Error Message If Missing"))
                        )
                )
        );
        return mandatoryFieldsChecks;
    }

    public static class InteractiveConsequence {

        private Performable[] actions;

        public InteractiveConsequence(Performable[] actions) {
            this.actions = actions;
        }

        public static InteractiveConsequence attemptTo(Performable... actions) {
            return new InteractiveConsequence(actions);
        }

        public <T> Consequence<T> thenCheckThat(Question<? extends T> actual, Matcher<T> expected) {
            return seeThat(actual, expected).after(actions);
        }
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
                seeThat(RegistrationForm.errorMessages(), contains(errorMessage))
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

    @When("{actor} tries to register with the same email")
    public void triesToRegisterWithTheSameEmail(Actor actor) {
        String existingEmail = newMember.getEmail();
        Traveller travellerWithDuplicateEmail = TravellerPersona.withName(actor.getName()).withEmail(existingEmail);
        actor.attemptsTo(
                RegisterAsAFrequentFlyer.withMemberDetailsFrom(travellerWithDuplicateEmail)
        );

    }
}
