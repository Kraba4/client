package com.example.games;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.games.command.Chat;
import com.example.games.command.Disconnect;
import com.example.games.command.LogOut;
import com.example.games.command.LookSession;
import com.example.games.command.LookSessionUser;
import com.example.games.command.StartGame;
import com.example.games.command.UpdateChat;
import com.example.games.command.UserInfo;

import java.io.IOException;
import java.util.LinkedList;

public class LobbyActivity extends AppCompatActivity {

    ListView listView;
    public TextView chat;
    EditText message;
    View start;
    View configure;
    public ImageView currentGame;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        Menu.lobbyView = this;
        message = findViewById(R.id.chat1);
        chat = findViewById(R.id.chat2);
        start = findViewById(R.id.play);
        currentGame = findViewById(R.id.current_game);
        configure = findViewById(R.id.setting);
        if(!Menu.isNewLobby) {
            start.setVisibility(View.INVISIBLE);
            configure.setVisibility(View.INVISIBLE);
        }

        chat.setMovementMethod(new ScrollingMovementMethod());
        listView = findViewById(R.id.list_players);
        Menu.usersAdapter = new UserInfoAdapter(this, R.layout.user_item, Menu.users);
        listView.setAdapter(Menu.usersAdapter);
        try {
            new LookSessionUser().send(Menu.out);
            new UpdateChat().send(Menu.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSend(View view) throws IOException {
        String text = message.getText().toString();
        if(!text.equals("")){
            new Chat(text).send(Menu.out);
            message.setText("");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickBack(View view) throws IOException {
        new Disconnect().send(Menu.out);
        new LookSession().send(Menu.out);
        Menu.isInMyLobby = false;
        finish();
    }
    public void onClickSetting(View view){
        Intent intent = new Intent(this, SettingLobbyActivity.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickPlay(View view){
        if(Menu.oldGameName!=null && !Menu.oldGameName.equals("none")){
            try {
                new StartGame(Menu.oldGameName).send(Menu.out);
                startGame(Menu.oldGameName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void startGame(String game){
        if(game.equals("tictac")) {
            Intent intent = new Intent(this, TicTacToeActivity.class);
            startActivity(intent);
        }
    }
}
