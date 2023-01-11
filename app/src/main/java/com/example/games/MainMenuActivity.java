package com.example.games;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.games.command.ConnectToSession;
import com.example.games.command.CreateSession;
import com.example.games.command.Disconnect;
import com.example.games.command.LogOut;
import com.example.games.command.LookSession;
import com.example.games.command.SessionInfo;

import java.io.IOException;

public class MainMenuActivity extends AppCompatActivity {
    ListView listView;
    View refresh;
    SessionInfoAdapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Menu.mainMenuView = this;

        refresh = findViewById(R.id.refresh);
        listView = findViewById(R.id.sessions);
        adapter = new SessionInfoAdapter(this, R.layout.list_item, Menu.sessions);
        Menu.sessionsAdapter = adapter;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView id = view.findViewById(R.id.id);
//                Menu.currentSession = (SessionInfo) adapterView.getItemAtPosition(i);
//                System.out.println(Menu.currentSession);
                try {
                    new ConnectToSession(Integer.parseInt(id.getText().toString())).send(Menu.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickRefresh(View view) throws IOException, InterruptedException {
        if(!Menu.isLookSessionInProcess) {
            Menu.isLookSessionInProcess = true;
            new LookSession().send(Menu.out);
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                refresh.setRotation((Float) animation.getAnimatedValue());
            }

        });
        animator.start();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickBack(View view) throws IOException {
        new LogOut().send(Menu.out);
        Menu.isLogOut = true;
        finish();
    }
    public void goToLobby(){
        Menu.isNewLobby = false;
        Intent intent = new Intent(this, LobbyActivity.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickAddLobby(View view) throws IOException {
        Menu.isNewLobby = true;
        new CreateSession().send(Menu.out);
        Intent intent = new Intent(this, LobbyActivity.class);
        startActivity(intent);
    }
}