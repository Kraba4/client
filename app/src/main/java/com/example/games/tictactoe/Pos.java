package com.example.games.tictactoe;

import java.io.Serializable;

public class Pos implements Serializable {
    public int i;
    public int j;

    public Pos(int i, int j) {
        this.i = i;
        this.j = j;
    }

}
