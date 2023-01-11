package com.example.games;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.games.command.Chat;
import com.example.games.command.Command;
import com.example.games.command.ConnectToSession;
import com.example.games.command.Login;
import com.example.games.command.LookSession;
import com.example.games.command.Register;
import com.example.games.command.SessionInfo;
import com.example.games.tictactoe.Restart;

import org.reflections.Reflections;

import java.io.IOException;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    EditText login;
    EditText password;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        Menu.loginAndRegisterView = this;
        login = findViewById(R.id.login);
        password = findViewById(R.id.register);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickLogIn(View view) throws IOException {
        if(Menu.socket ==null || !Menu.socket.getKeepAlive()) {
            Menu.connectToServer("192.168.0.102", 58888); //change ip
        }
        new ThreadLoginSaveSend().execute(login.getText().toString(), password.getText().toString());
    }
    public void goToMainMenu(){
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }


}