package com.example.games.command;

import java.io.IOException;

public class EndGameNotification extends Command {
    public String winner;

    public EndGameNotification(String winner) {
        this.winner = winner;
    }
}
