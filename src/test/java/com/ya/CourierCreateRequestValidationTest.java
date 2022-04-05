package com.ya;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ParametrizedTest {

    private final Courier courier;
    private final int expectedStatus;
    private final String expectedErrorMessage;

    public ParametrizedTest(Courier courier, int expectedStatus, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new  Object[][] {
                {CourierGenerator.getRandomLoginOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {CourierGenerator.getRandomPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {CourierGenerator.getRandomPasswordAndLogin(), 201, null}
        };
    }

    @Test
    public void invalidRequestIsNotAllowed() {

        Courier courier = CourierGenerator.getRandomLoginOnly();

        ValidatableResponse response = new CourierClient().create(courier);

        //создаем курьера только с логином, делаем запрос, проверяем - создание не состоялось

        String message = response.extract().path("message");

        assert message == expectedErrorMessage;
    }
}