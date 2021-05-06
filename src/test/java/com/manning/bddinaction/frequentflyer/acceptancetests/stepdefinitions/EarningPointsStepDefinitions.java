package com.manning.bddinaction.frequentflyer.acceptancetests.stepdefinitions;

import com.google.common.collect.Streams;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.AccountStatus;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.FlightBooking;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.FlightSearch;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.TravelClass;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.Traveller;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.UserLevel;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.api.UserAPI;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.myaccount.MyAccount;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.myaccount.StatusPanel;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.navigation.Navigate;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.search.BookTheFlight;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.search.SearchFlights;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.ux.Acknowledge;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.SoftAssertions;

import java.util.*;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class EarningPointsStepDefinitions {

    @ParameterType("\\d*")
    public Optional<Integer> points(String points) {
        return ((points == null) || points.isEmpty()) ? Optional.empty() : Optional.of(Integer.parseInt(points));
    }

    @ParameterType(".*")
    public UserLevel statusLevel(String statusLevel) {
        return UserLevel.valueOf(statusLevel.toUpperCase());
    }

    @DataTableType
    public AccountStatus accountStatus(Map<String, String> statusValues) {
        return new AccountStatus(
                UserLevel.valueOf(statusValues.get("Status Level").toUpperCase()),
                Integer.parseInt(statusValues.get("Point Balance")));
    }

    @DataTableType
    public FlightSearch flightSearch(Map<String,String> flight) {
        return new FlightSearch(flight.get("From"),
                                flight.get("To"),
                                TravelClass.withLabel(flight.get("Travel Class")),
                                flight.get("Trip Type").equalsIgnoreCase("Return"));
    }

    @DataTableType
    public FlightBooking flightBooking(Map<String,String> flight) {
        return new FlightBooking(flight.get("Departure"),
                                 flight.get("Destination"),
                                 Integer.parseInt(flight.get("Points Earned")));
    }

    @Steps
    UserAPI userAPI;

    List<Integer> pointsToCheck = new ArrayList<>();

    @When("{actor} earns between {points} and {points} points")
    public void heEarnsBetween(Actor traveller, Optional<Integer> minPoints, Optional<Integer> maxPoints) {
        minPoints.ifPresent(
                points -> pointsToCheck.add(points)
        );
        maxPoints.ifPresent(
                points -> pointsToCheck.add(points)
        );
    }

    @Then("his/her status should become {statusLevel}")
    public void hisStatusShouldBecomeStatusLevel(UserLevel statusLevel) {
        Traveller traveller = theActorInTheSpotlight().recall("CURRENT_USER");

        SoftAssertions softly = new SoftAssertions();
        pointsToCheck.forEach(
                points -> {
                    Traveller updatedTravellerDetails = userAPI.assignPoints(traveller, points);
                    softly.assertThat(updatedTravellerDetails.getUserLevel()).isEqualTo(statusLevel);
                }
        );
        softly.assertAll();
    }

    @Then("{actor} should earn {int} points")
    public void shouldEarnPoints(Actor traveller, int expectedPoints) {
        traveller.attemptsTo(Navigate.toMyAccount());

        FlightBooking bookedFlight = traveller.asksFor(MyAccount.flightHistory())
                                              .stream().reduce((first, second) -> second)
                                              .orElseThrow(() -> new AssertionError("No bookings found"));

        assertThat(bookedFlight.pointsEarned()).isEqualTo(expectedPoints);
    }

    @Then("his/her point balance should be {int} points")
    public void shouldEarnPoints(int expectedPoints) {
        Traveller traveller = theActorInTheSpotlight().recall("CURRENT_USER");
        int pointBalance = userAPI.findUserById(traveller.getUserId()).getPoints();
        assertThat(pointBalance).isEqualTo(expectedPoints);
//
//        theActorInTheSpotlight().attemptsTo(
//                Navigate.toMyAccount(),
//                Ensure.thatTheAnswerTo(MyAccount.pointBalance()).isEqualTo(expectedPoints)
//        );
    }

    @When("{actor} books the following flights:")
    public void booksTheFollowingFlights(Actor traveller, List<FlightSearch> flights) {
        flights.forEach(
                flight -> traveller.attemptsTo(
                        SearchFlights.from(flight.from())
                                .to(flight.to())
                                .inTravelClass(flight.travelClass())
                                .withAReturnJourney(flight.returnTrip()),
                        BookTheFlight.thatIsFirstInTheList(),
                        Acknowledge.success()
                )
        );
    }

    @Then("his/her booking history should contain:")
    public void hisBookingHistoryShouldContain(List<FlightBooking> flightHistory) {
        theActorInTheSpotlight().attemptsTo(
            Navigate.toMyAccount(),
            Ensure.thatTheListOf(MyAccount.flightHistory())
                  .containsExactlyInAnyOrderElementsFrom(flightHistory)
        );
    }

    @And("his/her account status should become:/have:")
    public void accountStatusShouldBecome(AccountStatus accountStatus) {
        Ensure.enableSoftAssertions();
        theActorInTheSpotlight().attemptsTo(
                Navigate.toMyAccount(),
                Ensure.thatTheAnswerTo(MyAccount.pointBalance()).isEqualTo(accountStatus.pointBalance()),
                Ensure.thatTheAnswerTo(MyAccount.statusLevel()).isEqualTo(accountStatus.userLevel())
        );
        Ensure.reportSoftAssertions();
    }
}
