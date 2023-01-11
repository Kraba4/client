package com.example.games;

import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.games.command.Command;
import com.example.games.command.EndGameNotification;
import com.example.games.command.JsonCommand;
import com.example.games.command.LookSession;
import com.example.games.command.LookSessionUser;
import com.example.games.command.LookSessionUserNotification;
import com.example.games.command.Message;
import com.example.games.command.SendGameData;
import com.example.games.command.SessionInfo;
import com.example.games.command.StartGameNotification;
import com.example.games.command.UpdateChat;
import com.example.games.command.UpdateChatNotification;
import com.example.games.command.UserInfo;
import com.example.games.tictactoe.Restart;
import com.example.games.tictactoe.TicTacToe;
import com.example.games.tictactoe.Turn;
import com.google.gson.Gson;

import org.reflections.Reflections;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

public class InputThread extends Thread{
    BufferedReader in;
    public boolean isRunning;
    private TicTacToe game;
    public InputThread(TicTacToe game, BufferedReader in) {
        this.game = game;
        this.in = in;
        isRunning = true;
    }
    private Object takeObject(String dataJson, Gson gson){
        Object data = null;
        JsonCommand jsonCommand = gson.fromJson(dataJson, JsonCommand.class);
        for( Class cl : Command.classes){
            if(cl.getSimpleName().equals(jsonCommand.type)){
                data = gson.fromJson(jsonCommand.json, cl);
                break;
            }
        }
        return data;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        super.run();
        try {
            Gson gson = new Gson();
            while (isRunning) {
                String dataJson = in.readLine();
                Object data = takeObject(dataJson, gson);
                if(data==null) continue;
                JsonCommand jsonCommand = gson.fromJson(dataJson, JsonCommand.class);
                for( Class cl : Command.classes){
                    if(cl.getSimpleName().equals(jsonCommand.type)){
                        data = gson.fromJson(jsonCommand.json, cl);
                        break;
                    }
                }
                if(data==null) continue;
                if(data instanceof SendGameData){
                    SendGameData sendGameData = (SendGameData) data;
                    for( Class cl : TicTacToe.classes){
                        if(cl.getSimpleName().equals(sendGameData.type)){
                            data = gson.fromJson(sendGameData.dataJson, cl);
                            break;
                        }
                    }
                }
                if(data==null) continue;
                if(data instanceof LookSession){
                    LinkedList<SessionInfo> sessions = new LinkedList<SessionInfo>();
                    LookSession lookSession = (LookSession)data;
                    if(lookSession.sessionInfo!=null) {
                        sessions.add(lookSession.sessionInfo);
                    }
                    while(lookSession.hasNext){
                        dataJson = in.readLine();
                        data = takeObject(dataJson, gson);
                        lookSession = (LookSession)data;
                        if(lookSession.sessionInfo!=null) {
                            sessions.add(lookSession.sessionInfo);
                        }
                    }

                    Menu.updateSessions(sessions);
                    System.out.println(sessions);
                }
                if(data instanceof LookSessionUserNotification){
                    new LookSessionUser().send(Menu.out);
                }
                if(data instanceof LookSessionUser){
                    LinkedList<UserInfo> users = new LinkedList<UserInfo>();
                    LookSessionUser lookSession = (LookSessionUser)data;
                    users.add(lookSession.user);
                    while(lookSession.hasNext){
                        dataJson = in.readLine();
                        data = takeObject(dataJson, gson);
                        lookSession = (LookSessionUser)data;
                        if(lookSession.user!=null) {
                            users.add(lookSession.user);
                        }
                    }

                    Menu.updateUsers(users);
                    System.out.println(users);
                }
                if(data instanceof UpdateChatNotification){
                    if(Menu.lobbyView != null) {
                        new UpdateChat().send(Menu.out);
                    }
                }
                if(data instanceof  UpdateChat){
                    Menu.updateChat(((UpdateChat)data).chat);
                    System.out.println(((UpdateChat)data).chat);
                }
                if(data instanceof Turn){
                    Menu.ticTacToeActivity.ticTacView.game.makeTurn((Turn) data);
                    Menu.ticTacToeActivity.ticTacView.invalidate();
                }
                if(data instanceof Restart){
                    Menu.ticTacToeActivity.ticTacView.game.newGame(((Restart)data).initiatorId, 3,3);
                    Menu.ticTacToeActivity.ticTacView.invalidate();
                }
//                if(data instanceof  Boolean){
//                    //TODO
////                    Menu.opponentChoice = (Boolean) data;
////                    Menu.ticTacToeActivity.runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            Menu.ticTacToeActivity.restart();
////                        }
////                    });
//                }
                if(data instanceof Message){
                    Message message = (Message) data;
                    if(message.text.equals("Login success")){
                       Menu.loginAndRegisterView.goToMainMenu();

                    }
                    if(message.text.equals("Connection success")){
                        Menu.mainMenuView.goToLobby();
                    }

                }
//                if(data instanceof LookSessionUserNotification){
//                    new StartGame().send(game.out);
//                }
                if(data instanceof StartGameNotification){
                    StartGameNotification startGameNotification = (StartGameNotification)data;

                    Menu.lobbyView.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Menu.lobbyView.startGame(startGameNotification.gameName);
                        }
                    });
                }
                if(data instanceof EndGameNotification){

                    Menu.ticTacToeActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Menu.ticTacToeActivity.winNotMe();
                        }
                    });
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
