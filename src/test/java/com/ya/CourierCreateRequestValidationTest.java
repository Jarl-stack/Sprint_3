package com.ya;

import com.ya.apiclient.CourierClient;
import com.ya.model.Courier;
import com.ya.utils.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierCreateRequestValidationTest {

    private final Courier courier;
    private final int expectedStatus;
    private final String expectedErrorMessage;

    public CourierCreateRequestValidationTest(Courier courier, int expectedStatus, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {CourierGenerator.getRandomLoginOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {CourierGenerator.getRandomPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {CourierGenerator.getRandomLoginAndPassword(), 201, null}
        };
    }

    @Test
    @DisplayName("Parametrized login courier test")
    @Description("Login with only login, only password, login and password")
    public void invalidRequestIsNotAllowed() {

        ValidatableResponse response = new CourierClient().create(courier);

        int actualStatus = response.extract().statusCode();
        String actualErrorMessage = response.extract().path("message");

        assertEquals("Value in 'message' doesn't match expected value", expectedErrorMessage, actualErrorMessage);
        assertEquals("Status cod doesn't match expected value", expectedStatus, actualStatus);
    }
}