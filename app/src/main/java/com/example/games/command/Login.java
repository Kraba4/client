package com.example.games.command;

import java.io.IOException;

public class Login extends Command {
    String login;
    String password;

    public Login(String login, String password) {
        hasNext = false;
        this.login = login;
        this.password = password;
    }



}
