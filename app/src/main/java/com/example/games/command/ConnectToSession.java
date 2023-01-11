package com.example.games.command;


import java.io.IOException;

public class ConnectToSession extends Command {
    int sessionId;
    String password = "";
    public ConnectToSession(int sessionId) {
        this.sessionId = sessionId;
    }
    public ConnectToSession(int sessionId, String password) {
        this.sessionId = sessionId;
        this.password = password;
    }
}
