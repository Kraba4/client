package com.example.games.command;

import java.io.IOException;
import java.util.LinkedList;

public class LookSessionUser extends Command {

    public UserInfo user;
    public LookSessionUser() {}
    public LookSessionUser(UserInfo user ) {
        this.user = user;
    }

}
