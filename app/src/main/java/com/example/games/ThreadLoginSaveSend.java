package com.example.games;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.games.command.Login;
import com.example.games.command.Register;

import java.io.IOException;

public class ThreadLoginSaveSend extends AsyncTask<String, Void, Void> {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(String... strings) {
        while(Menu.out == null || Menu.isLogOut){

        }
        try {
            new Register(strings[0], strings[1]).send(Menu.out);
            new Login(strings[0], strings[1]).send(Menu.out);
            Menu.myLogin = strings[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
