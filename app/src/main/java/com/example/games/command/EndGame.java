package com.example.games.command;

import java.io.IOException;

public class EndGame extends Command {
    String winner;

    public EndGame(String winnner) {
        this.winner = winnner;
    }
}
