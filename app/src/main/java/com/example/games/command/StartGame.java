package com.example.games.command;

import java.io.IOException;

public class StartGame extends Command {
    String gameName;

    public StartGame(String gameName) {
        this.gameName = gameName;
    }
}
