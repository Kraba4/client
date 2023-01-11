package com.example.games.command;


import java.io.IOException;
import java.util.LinkedList;

public class LookSession extends Command{
    public SessionInfo sessionInfo;
    public int sessionId = -1;
    public LookSession() {
    }
    public LookSession(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public LookSession(int sessionId) {
        this.sessionId = sessionId;
    }
}
