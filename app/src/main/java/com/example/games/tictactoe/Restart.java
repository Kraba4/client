package com.example.games.tictactoe;

import java.io.Serializable;

public class Restart implements Serializable {
    public int initiatorId;

    public Restart(int initiatorId) {
        this.initiatorId = initiatorId;
    }
}
