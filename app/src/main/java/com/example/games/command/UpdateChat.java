package com.example.games.command;

import java.io.IOException;
import java.util.LinkedList;

public class UpdateChat extends Command {
    public LinkedList<String> chat;

    public UpdateChat(LinkedList<String> chat) {
        this.chat = chat;
    }

    public UpdateChat() {
    }
}
