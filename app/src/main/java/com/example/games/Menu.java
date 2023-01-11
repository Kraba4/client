package com.example.games;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.games.command.SessionInfo;
import com.example.games.command.UserInfo;
import com.example.games.tictactoe.TicTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

public class Menu {
    public static LinkedList<SessionInfo> sessions=new LinkedList<>();
    public static LinkedList<UserInfo> users=new LinkedList<>();
    public static SessionInfoAdapter sessionsAdapter;
    public static UserInfoAdapter usersAdapter;
    public static LinkedList<String> currentSessionChat = new LinkedList<>();
    public static LinkedList<String> currentSessionPlayers = new LinkedList<>();
    public static String myLogin;
    public static String myPassword;
    public static OutputStreamWriter out;
    public static BufferedReader in;
    public static TicTacToe game;
    public static MainActivity loginAndRegisterView;
    public static MainMenuActivity mainMenuView;
    public static LobbyActivity  lobbyView;
    public static TicTacToeActivity ticTacToeActivity;
    public static TicTacView gameView;
    public static InputThread inputThread;
    public static Socket socket;
    public static boolean isLookSessionInProcess;
    public static boolean isLogOut = false;
    public static boolean isNewLobby = false;
    public static int currentId = -1;
    public static boolean isInMyLobby = false;
    public static boolean opponentChoice = false;

    public static String oldPassword;
    public static String oldNPlayers;
    public static String oldGameName;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void updateChat(LinkedList<String> s){
        StringBuilder buf = new StringBuilder();
        for(String line : s){
            buf.append(line).append("\n\n");
        }
        lobbyView.chat.setText(buf.toString());
    }
    public static void updateUsers(LinkedList<UserInfo> s){
        users.clear();
        users.addAll(s);
        lobbyView.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                usersAdapter.notifyDataSetChanged();
            }
        });

    }
    public static void updateSessions(LinkedList<SessionInfo> s){

        sessions.clear();
        sessions.addAll(s);
        isLookSessionInProcess = false;
        mainMenuView.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                sessionsAdapter.notifyDataSetChanged();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean connectToServer(String ip, int port) throws IOException {
//        if (inputThread != null) {
//            inputThread.stop();
//        }
//        if(socket!=null){
//            socket.close();
//        }
       new ConnectToServerTask().execute(ip, String.valueOf(port));
        return true;
    }
    static class ConnectToServerTask extends AsyncTask<String, Void, Void>{

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(String... strings) {
            try {
                socket = new Socket(strings[0], Integer.parseInt(strings[1]));
                out = new OutputStreamWriter(socket.getOutputStream());
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                inputThread = new InputThread(game, in);
                inputThread.start();
                isLogOut = false;
                //TODO КОСТЫЛЬ
//                new Register("player1", "123").send(Menu.out);
//                new Login("player1", "123").send(Menu.out);
//                new ConnectToSession(0).send(Menu.out);
//                new LookSessionUser().send(Menu.out);
            } catch (IOException e) {

            }
            return null;
        }
    }
}
