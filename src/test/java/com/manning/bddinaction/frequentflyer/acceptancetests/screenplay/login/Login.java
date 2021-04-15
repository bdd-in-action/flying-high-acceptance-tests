package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.login;

import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.Traveller;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.navigation.Navigate;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SendKeys;
import net.serenitybdd.screenplay.targets.Target;

public class Login {
    public static Performable as(Traveller traveller) {
        return Task.where("{0} logs in as " + traveller.getEmail(),
                Navigate.toTheLoginPage(),
                SendKeys.of(traveller.getEmail()).into(LoginForm.EMAIL),
                SendKeys.of(traveller.getPassword()).into(LoginForm.PASSWORD),
                Click.on(LoginForm.LOGIN_BUTTON)
        );
    }
}
