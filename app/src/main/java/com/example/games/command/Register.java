package com.example.games.command;

import java.io.IOException;

public class Register extends Command {
    String login;
    String password;

    public Register(String login, String password) {
        this.login = login;
        this.password = password;
    }



}
