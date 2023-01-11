package com.example.games.command;

import java.io.IOException;

public class LookSessionNotification extends Command {
    public int sessionId;

    public LookSessionNotification() {
    }

    public LookSessionNotification(int sessionId){
        this.sessionId = sessionId;
    }

}
