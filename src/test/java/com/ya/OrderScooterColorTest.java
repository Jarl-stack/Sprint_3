package com.ya;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class NewOrderTest {

    private final String[] color;
    int track;

    public NewOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {new String[] {"BLACK", "GRAY"}},
                {null},
                {new String[] {"BLACK"}}
        };
    }

    @Test
    public void orderCanBeCreated() {
        OrderClient orderClient = new OrderClient();
        Order order = new Order(color);

        ValidatableResponse response = orderClient.createOrder(order);
        track = response.extract().path("track");

        assertNotNull("track is null", track);

    }
}
