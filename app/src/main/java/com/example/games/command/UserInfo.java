package com.example.games.command;

import java.util.Map;

public class UserInfo {
    String login;
    Map<String, Integer> info;

    public UserInfo(String login, Map<String, Integer> info) {
        this.login = login;
        this.info = info;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setInfo(Map<String, Integer> info) {
        this.info = info;
    }

    public String getLogin() {
        return login;
    }

    public Map<String, Integer> getInfo() {
        return info;
    }
}
