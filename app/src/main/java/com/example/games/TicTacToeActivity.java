package com.example.games;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.games.command.EndGame;
import com.example.games.command.SendGameData;
import com.example.games.command.StartGame;
import com.example.games.command.UserInfo;
import com.google.gson.Gson;

import java.io.IOException;

public class TicTacToeActivity extends AppCompatActivity {

    TicTacView ticTacView;
    Button exit;
    boolean choice;
    boolean win;
    Gson gson;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        win = false;
        gson = new Gson();
        setContentView(R.layout.activity_tic_tac_toe);
        Menu.ticTacToeActivity = this;
        ticTacView = findViewById(R.id.view);
        try {
            ticTacView.game.newGame(0, 3, 3);
            ticTacView.game.myId = Menu.isInMyLobby ? 0 : 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickExit(View view) throws IOException {
        if(ticTacView.game.winSet == null){
            String winnerLogin;
            for(UserInfo user : Menu.users){
                if(!user.getLogin().equals(Menu.myLogin)){
                    new EndGame(user.getLogin()).send(Menu.out);
                    finish();
                    break;
                }
            }
        }
    }
    //TODO проверить работу победы поражения надо для этого сделать send
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void win(){
        try {
            new EndGame(Menu.myLogin).send(Menu.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AwaitTask(this).execute();
//        exit.setText("Выйти");
//        win = true;
    }
    public void end(){
        finish();
    }
    public void winNotMe(){
        new AwaitTask(this).execute();
    }
    //TODO
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void restart(){
//        if(choice && Menu.opponentChoice) {
//            exit.setText("Сдаться");
//            win = false;
//            try {
//                new EndGame("").send(Menu.out);
//                new StartGame("tictac").send(Menu.out);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void onClickRestart(View view) throws IOException {
//        choice = !choice;
//        new SendGameData(gson.toJson(choice), Boolean.class.getSimpleName()).send(Menu.out);
//    }
}