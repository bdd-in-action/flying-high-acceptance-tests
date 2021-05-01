package com.manning.bddinaction.frequentflyer.acceptancetests.stepdefinitions.usingpageobjects;

import com.manning.bddinaction.frequentflyer.acceptancetests.domain.FrequentFlyer;
import com.manning.bddinaction.frequentflyer.acceptancetests.pageobjects.CurrentUser;
import com.manning.bddinaction.frequentflyer.acceptancetests.pageobjects.LoginPage;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.api.UserAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationStepDefinitions {

    FrequentFlyer frequentFlyer;

    private UserAPI userAPI;

    private LoginPage loginPage() { return new LoginPage(WebTestSupport.currentDriver()); }

    @Given("{} is a registered Frequency Flyer member")
    public void frequentFlyerMember(FrequentFlyer frequentFlyer) {
        this.frequentFlyer = frequentFlyer;
    }

    @When("^s?he (?:logs|has logged) on with a valid username and password$")
    public void logsOnWithAValidUsernameAndPassword() {
        LoginPage loginPage = new LoginPage(WebTestSupport.currentDriver());
        loginPage.open();
        loginPage.signinWithCredentials(frequentFlyer.email, frequentFlyer.password);
    }

    @Then("he/she should be given access to his/her account")
    public void heShouldBeGivenAccessToHisAccount() {
        CurrentUser currentUser = new CurrentUser(WebTestSupport.currentDriver());
        assertThat(currentUser.email()).isEqualTo(frequentFlyer.email);
    }

//    @Given("{traveller} has logged onto the Frequent Flyer application as a new member")
//    public void loginAsANewMember(Traveller traveller) {
//        WebDriver driver = WebTestSupport.currentDriver();
//        LoginPage loginForm = new LoginPage(driver);
//
//        Traveller newTraveller = traveller.withAUniqueEmailAddress();
//        userAPI.createNewUser(newTraveller);
//
//        loginForm.open();
//        loginForm.signinWithCredentials(newTraveller.getEmail(), newTraveller.getPassword());
//    }
//
//    @And("his/her account status should become:/have:")
//    public void accountStatusShouldBecome(AccountStatus accountStatus) {
//        navigate().toMyAccount();
//
//        Ensure.enableSoftAssertions();
//        theActorInTheSpotlight().attemptsTo(
//                com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.navigation.Navigate.toMyAccount(),
//                Ensure.thatTheAnswerTo(MyAccount.pointBalance()).isEqualTo(accountStatus.pointBalance()),
//                Ensure.thatTheAnswerTo(MyAccount.statusLevel()).isEqualTo(accountStatus.userLevel())
//        );
//        Ensure.reportSoftAssertions();
//    }

    //
    // SCREENPLAY STEP DEFINITIONS
    //
//
//    @Given("{actor} is a new Frequent Flyer Member")
//    public void aNewFrequentFlyerMember(Actor traveller) {
//        traveller.attemptsTo(
//                RegisterAsAFrequentFlyer.viaTheAPI().withMemberDetailsFrom(
//                        TravellerPersona.withName(traveller.getName())
//                                        .withAUniqueEmailAddress()
//                )
//        );
//    }
//
//    @Given("{actor} is a Frequent Flyer Member with status {statusLevel}")
//    public void aNewFrequentFlyerMember(Actor traveller, UserLevel statusLevel) {
//        traveller.attemptsTo(
//                RegisterAsAFrequentFlyer.viaTheAPI().withMemberDetailsFrom(
//                        TravellerPersona.withName(traveller.getName())
//                                .withAUniqueEmailAddress()
//                                .withLevel(statusLevel)
//                )
//        );
//    }
//
//    @When("{actor} has logged onto the Frequent Flyer application")
//    public void loginAs(Actor member) {
//        Traveller currentUser = member.recall("CURRENT_USER");
//        member.attemptsTo(
//                Login.as(currentUser),
//                Acknowledge.successMessageOf("Logged in as " + currentUser.getEmail())
//        );
//    }

}
