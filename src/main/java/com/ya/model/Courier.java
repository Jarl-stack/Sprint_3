package com.ya.model;

public class Courier {

    private String login;
    private String password;
    private String firsName;

    public Courier() {
    }

    public Courier setLogin(String login) {
        this.login = login;
        return this;
    }

    public Courier setPassword(String password) {
        this.password = password;
        return this;
    }

    public Courier setLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
        return this;
    }

    public Courier setFirstName(String firsName) {
        this.firsName = firsName;
        return this;
    }

    public Courier(String login, String password, String firsName) {
        this.login = login;
        this.password = password;
        this.firsName = firsName;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getFirsName() {
        return firsName;
    }
}