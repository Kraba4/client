package com.example.games;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class ThreadTexting extends AsyncTask<String, Void, Void> {
    OutputStreamWriter out;
    public ThreadTexting(OutputStreamWriter out){
        this.out = out;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            out.write(strings[0] + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
