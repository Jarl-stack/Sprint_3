package com.ya;

import com.ya.apiclient.CourierClient;
import com.ya.model.Courier;
import com.ya.utils.CourierCredentials;
import com.ya.utils.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CourierLoginTest {

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
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Login courier with valid credentials")
    @Description("Login for courier and extract id from response")
    public void courierCanLoginWithValidCredentialsTest() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        int statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");
        boolean actual = courierId > 0;

        assertEquals("Courier cannot login", 200, statusCode);
        assertEquals("Courier ID is incorrect", true, actual);
    }
}