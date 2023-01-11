package com.example.games.command;

import java.io.IOException;

public class Message extends Command {

    public String from;
    public String text;

    public Message(String from, String text) {
        this.from = from;
        this.text = text;
    }


}
