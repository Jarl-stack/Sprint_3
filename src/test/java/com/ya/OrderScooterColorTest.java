package com.ya;

import com.ya.apiclient.OrderClient;
import com.ya.model.Order;
import com.ya.utils.OrderGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrderScooterColorTest {

    private final String[] color;
    int track;

    public OrderScooterColorTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {new String[]{"BLACK", "GRAY"}},
                {null},
                {new String[]{"BLACK"}}
        };
    }

    @Test
    @DisplayName("Scooter order create with different colors")
    public void orderCanBeCreated() {
        OrderClient orderClient = new OrderClient();

        Order order = OrderGenerator.getOrderData().setColor(color);
        ValidatableResponse response = orderClient.createOrder(order);

        track = response.extract().path("track");
        assertNotNull("track is null", track);
    }
}