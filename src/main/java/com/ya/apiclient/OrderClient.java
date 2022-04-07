package com.ya.apiclient;

import com.ya.model.Order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends ScooterRestClient {

    private static final String ORDER_PATH = "api/v1/orders/";

    @Step("Creating order with body")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Get order id by track {track}")
    public ValidatableResponse getOrderId(int track) {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH + "track?t=" + track)
                .then();
    }

    @Step("Cancel order by track {track}")
    public ValidatableResponse cancel(int track) {
        return given()
                .spec(getBaseSpec())
                .body(track)
                .when()
                .put(ORDER_PATH + "cancel")
                .then();
    }

    @Step("Take order by courier with order id {orderId} and courier id {courierId}")
    public ValidatableResponse takeOrder(int orderId, int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .put(ORDER_PATH + "accept/" + orderId + "?courierId=" + courierId)
                .then();
    }

    @Step("Get orders list by courier id {courierId} ")
    public ValidatableResponse getOrdersList(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH + "?courierId=" + courierId)
                .then();
    }
}