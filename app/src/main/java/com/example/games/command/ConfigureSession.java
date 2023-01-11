package com.example.games.command;


import java.io.IOException;

public class ConfigureSession extends Command {
    public int maxUsers;
    public String password;
    public String gameName;
    public ConfigureSession(int maxUsers, String password, String gameName) {
        this.maxUsers = maxUsers;
        this.password = password;
        this.gameName = gameName;
    }

}
