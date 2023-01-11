package com.example.games.command;

import java.io.IOException;

public class StartGameNotification extends Command {
    public String gameName;

    public StartGameNotification(String gameName) {
        this.gameName = gameName;
    }

}
