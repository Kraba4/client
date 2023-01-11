package com.example.games.command;

import java.io.IOException;

public class SendGameData extends Command {
    public String dataJson;
    public String type;
    public SendGameData(String dataJson, String type) {
        this.dataJson = dataJson;
        this.type = type;
    }

}
