package com.example.games.command;


import java.io.IOException;

public class CreateSession extends Command {
    int maxUsers;
    String password="";
    public CreateSession() {
        this.maxUsers = 2;
    }
    public CreateSession(int maxUsers) {
        this.maxUsers = maxUsers;
    }
    public CreateSession(int maxUsers, String password) {
        this.maxUsers = maxUsers;
        this.password = password;
    }
    public CreateSession(String password) {
        this.maxUsers = 2;
        this.password = password;
    }

}
