package com.example.games;

import android.os.AsyncTask;
import android.view.View;

public class AwaitTask extends AsyncTask<String, Void, Void> {
    TicTacToeActivity view;
    public AwaitTask(TicTacToeActivity view){
        this.view = view;
    }
    @Override
    protected Void doInBackground(String... strings) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        Menu.ticTacToeActivity.end();
    }
}
