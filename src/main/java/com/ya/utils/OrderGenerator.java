package com.ya.utils;

import com.ya.model.Order;
import io.qameta.allure.Step;

public class OrderGenerator {

    @Step("create data for order")
    public static Order getOrderData() {
        final String firstName = "Naruto";
        final String lastName = "Uchiha";
        final String address = "Konoha, 142 apt.";
        final int metroStation = 4;
        final String phone = "+7 800 355 35 35";
        final int rentTime = 5;
        final String deliveryDate = "2020-06-06";
        final String comment = "Saske, come back to Konoha";
        final String[] color = {};
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}