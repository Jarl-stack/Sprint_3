package com.ya;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class OrdersTest {

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

    //Add order cancel method

    @Test
    public void getOrdersReturnsTest() {

        order = OrderGenerator.setData();

        //Create order and extract "track" to var track
        ValidatableResponse orderResponse = orderClient.createOrder(order);
        track = orderResponse.extract().path("track");

        //get order by track and extract to var orderId
        ValidatableResponse orderIdResponse = orderClient.getOrderId(track);
        orderId = orderIdResponse.extract().path("order.id");

        //Login courier and extract "id" to var courierId
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.extract().path("id");

        //Take order
        orderClient.takeOrder(orderId, courierId);

        ValidatableResponse response = orderClient.getOrdersList(courierId);
        List<Object> orders = response.extract().jsonPath().getList("orders");
        assertFalse(orders.isEmpty());

    }

}
