package com.ya;

import com.ya.apiclient.CourierClient;
import com.ya.apiclient.OrderClient;
import com.ya.model.Courier;
import com.ya.model.Order;
import com.ya.utils.CourierCredentials;
import com.ya.utils.CourierGenerator;
import com.ya.utils.OrderGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GetOrdersListTest {

    CourierClient courierClient;
    Courier courier;
    int courierId;
    OrderClient orderClient;
    Order order;
    int track;
    int orderId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        courierClient.create(courier);
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
        orderClient.cancel(track);
    }

    @Test
    @DisplayName("Get orders list")
    @Description("Create order, get order Id, take order for courier, get orders list")
    public void getOrdersReturnsTest() {

        order = OrderGenerator.getOrderData();

        ValidatableResponse orderResponse = orderClient.createOrder(order);
        track = orderResponse.extract().path("track");

        ValidatableResponse orderIdResponse = orderClient.getOrderId(track);
        orderId = orderIdResponse.extract().path("order.id");

        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.extract().path("id");

        orderClient.takeOrder(orderId, courierId);

        ValidatableResponse response = orderClient.getOrdersList(courierId);
        List<Object> orders = response.extract().jsonPath().getList("orders");
        assertFalse(orders.isEmpty());
    }
}