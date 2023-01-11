package com.example.games;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.games.command.ConfigureSession;
import com.example.games.command.CreateSession;
import com.example.games.command.Disconnect;
import com.example.games.command.LookSession;

import java.io.IOException;

public class SettingLobbyActivity extends AppCompatActivity {
    EditText password;
    SeekBar seekBar;
    TextView nPlayers;
    ImageView none;
    ImageView knight;
    ImageView tictac;
    String gameName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_lobby);
        password = findViewById(R.id.edit_password);
        nPlayers = findViewById(R.id.n_players);
        none = findViewById(R.id.var_none);
        knight = findViewById(R.id.var_chess);
        tictac = findViewById(R.id.var_tictac);
        seekBar = findViewById(R.id.seek);
        gameName = "none";
        if(Menu.isInMyLobby) {
            password.setText(Menu.oldPassword);
            nPlayers.setText(Menu.oldNPlayers);
            gameName = Menu.oldGameName;
        }
        if(gameName.equals("none")){
            none.setAlpha(1F);
            knight.setAlpha(0.2F);
            tictac.setAlpha(0.2F);
        }else if(gameName.equals("chess")){
            none.setAlpha(0.2F);
            knight.setAlpha(1F);
            tictac.setAlpha(0.2F);
        }else if(gameName.equals("tictac")){
            none.setAlpha(0.2F);
            knight.setAlpha(0.2F);
            tictac.setAlpha(1F);
        }
        seekBar.setProgress(Integer.parseInt(nPlayers.getText().toString()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                nPlayers.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void onClickBack(View view) throws IOException {
        finish();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSave(View view) throws IOException {
        new ConfigureSession(Integer.parseInt(nPlayers.getText().toString()),
                password.getText().toString(),gameName).send(Menu.out);
//        new LookSession(999).send(Menu.out);
        Menu.isInMyLobby = true;
        Menu.oldPassword = password.getText().toString();
        Menu.oldNPlayers = nPlayers.getText().toString();
        Menu.oldGameName = gameName;
        if(Menu.oldGameName != null) {
            if (Menu.oldGameName.equals("none")) {
                Menu.lobbyView.currentGame.setImageResource(R.drawable.what);
            } else if (Menu.oldGameName.equals("chess")) {
                Menu.lobbyView.currentGame.setImageResource(R.drawable.black_knight);
            } else if (Menu.oldGameName.equals("tictac")) {
                Menu.lobbyView.currentGame.setImageResource(R.drawable.tictac);
            }
        }
        finish();
    }
    public void onClickFirst(View view){
        none.setAlpha(1F);
        gameName = "none";
        knight.setAlpha(0.2F);
        tictac.setAlpha(0.2F);
    }
    public void onClickSecond(View view){
        none.setAlpha(0.2F);
        gameName = "chess";
        knight.setAlpha(1F);
        tictac.setAlpha(0.2F);
    }
    public void onClickThird(View view){
        none.setAlpha(0.2F);
        gameName = "tictac";
        knight.setAlpha(0.2F);
        tictac.setAlpha(1F);
    }

}