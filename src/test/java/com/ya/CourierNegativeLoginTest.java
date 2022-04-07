package com.ya;

import com.ya.apiclient.CourierClient;
import com.ya.model.Courier;
import com.ya.utils.CourierCredentials;
import com.ya.utils.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CourierNegativeLoginTest {

    CourierClient courierClient;
    Courier courier;
    int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        ValidatableResponse response = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = response.extract().path("id");
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Login without login field")
    @Description("Courier cannot login without filled login")
    public void courierCannotLoginWithoutLoginTest() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("", courier.getPassword()));
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertEquals("Status code is incorrect", 400, statusCode);
        assertEquals("message is incorrect", "Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Login without password field")
    @Description("Courier cannot login without filled password field")
    public void courierCannotLoginWithoutPasswordTest() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), ""));
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertEquals("Status code is incorrect", 400, statusCode);
        assertEquals("message is incorrect", "Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Login with non existent credentials")
    @Description("Courier cannot login with wrong credentials")
    public void courierCannotLoginWithNonexistentCredentialsTest() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10)));
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertEquals("Status code is incorrect", 404, statusCode);
        assertEquals("message is incorrect", "Учетная запись не найдена", message);
    }

    @Test
    @DisplayName("Login with wrong password")
    @Description("Courier cannot login with wrong password")
    public void courierCannotLoginWithWrongPassword() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), RandomStringUtils.randomAlphabetic(10)));
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertEquals("Status code is incorrect", 404, statusCode);
        assertEquals("Message in body isn't correct", "Учетная запись не найдена", message);
    }
}
