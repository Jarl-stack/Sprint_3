package com.ya.utils;

import com.ya.model.Courier;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    @Step("Create random courier data")
    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    @Step("Create only random login for courier")
    public static Courier getRandomLoginOnly() {
        return new Courier().setLogin(RandomStringUtils.randomAlphabetic(10));
    }

    @Step("Create only random password for courier")
    public static Courier getRandomPasswordOnly() {
        return new Courier().setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    @Step("Create random password and random login for courier")
    public static Courier getRandomLoginAndPassword() {
        return new Courier().setLoginAndPassword(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
    }
}