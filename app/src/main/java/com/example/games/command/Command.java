package com.example.games.command;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.games.ThreadTexting;
import com.google.gson.Gson;


import org.reflections.Reflections;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public abstract class Command implements Serializable {
    public static Class[] classes = {Chat.class, ConfigureSession.class, Register.class,
            Login.class, CreateSession.class, Message.class, ConnectToSession.class, SendGameData.class,
    LookSession.class,LookSessionUser.class,LookSessionUserNotification.class,
    UpdateChat.class, UpdateChatNotification.class, Disconnect.class, LogOut.class, StartGame.class,
    StartGameNotification.class, EndGame.class, EndGameNotification.class, LookSessionNotification.class};
    public boolean hasNext = false;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void send(OutputStreamWriter out) throws IOException {
        Gson gson = new Gson();
        System.out.println("**!!!*");
        for( Class cl : classes){
            System.out.println("***");
            if(cl.isInstance(this)){
                System.out.println(cl.getSimpleName());
                String json = gson.toJson(this);
                JsonCommand jsonCommand = new JsonCommand(cl.getSimpleName(), json);
                String json2 = gson.toJson(jsonCommand);
                new ThreadTexting(out).execute(json2);
            }
        }

    }
    public void send(OutputStreamWriter out, boolean hasNext) throws IOException {
        this.hasNext = true;
        Gson gson = new Gson();
        for( Class cl : classes){
            if(cl.isInstance(this)){
                String json = gson.toJson(this);
                JsonCommand jsonCommand = new JsonCommand(cl.getSimpleName(), json);
                String json2 = gson.toJson(jsonCommand);
                new ThreadTexting(out).execute(json2);
            }
        }
    }
}
