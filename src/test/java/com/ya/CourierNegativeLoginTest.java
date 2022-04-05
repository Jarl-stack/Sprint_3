package com.ya;

import io.restassured.response.ValidatableResponse;
import org.apache.http.auth.Credentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierLoginWithEmptyRequiredFieldsTest {

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
    public void courierCannotLoginWithoutLoginTest() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("", courier.getPassword()));
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("message is incorrect", message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void courierCannotLoginWithoutPasswordTest() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), ""));
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("message is incorrect", message, equalTo("Недостаточно данных для входа"));

    }

}
