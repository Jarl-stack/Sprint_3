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

public class CourierCreateTest {

    CourierClient courierClient;
    Courier courier;
    boolean bodyJSON;
    int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Create courier with valid data")
    @Description("Create courier and login for extracting courier id")
    public void courierCanCreateWithValidDataTest() {
        ValidatableResponse createResponse = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.extract().path("id");

        int statusCode = createResponse.extract().statusCode();
        bodyJSON = createResponse.extract().path("ok");

        assertEquals("Status cod is incorrect", 201, statusCode);
        assertEquals("Body is incorrect", true, bodyJSON);
    }

    @Test
    @DisplayName("Create courier with existed login")
    @Description("Courier cannot create if he already exist")
    public void courierCannotCreateWithExistedLoginTest() {
        courierClient.create(courier);
        ValidatableResponse createResponse = courierClient.create(courier);

        int statusCode = createResponse.extract().statusCode();

        assertEquals("Courier can login with the same login", 409, statusCode);
    }
}