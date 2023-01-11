package com.example.games;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.games.command.SessionInfo;

import java.util.LinkedList;

public class SessionInfoAdapter extends BaseAdapter {
    Context context;
    int resourceLayout;
    LinkedList<SessionInfo> sessions;
    SessionInfoAdapter(Context context, int resourceLayout, LinkedList<SessionInfo> sessions) {
        this.context = context;
        this.sessions = sessions;
        this.resourceLayout = resourceLayout;
    }
    @Override
    public int getCount() {
        return sessions.size();
    }

    @Override
    public Object getItem(int i) {
        return sessions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resourceLayout, null);
        }

        SessionInfo p = (SessionInfo) getItem(i);

        if (p != null) {
            TextView id = (TextView) v.findViewById(R.id.id);
            TextView owner = (TextView) v.findViewById(R.id.owner);
            ImageView hasPassword = (ImageView) v.findViewById(R.id.has_password);
            TextView players = (TextView) v.findViewById(R.id.players);
            ImageView gameImage = (ImageView) v.findViewById(R.id.game_image);

            id.setText(String.valueOf(p.getSessionId()));
            owner.setText(p.getOwnerLogin());
            if(p.isHasPassword()) {
                hasPassword.setImageResource(R.drawable.lock);
            }else {
                hasPassword.setImageResource(R.drawable.openlock);
            }
            players.setText(String.valueOf(p.getnUsers()) + "/" + String.valueOf(p.getMaxUsers()) );

            if(p.getGameName().equals("none")){
                gameImage.setImageResource(R.drawable.what);
            }else
            if(p.getGameName().equals("chess")){
                gameImage.setImageResource(R.drawable.black_knight);
            }else
            if(p.getGameName().equals("tictac")){
                gameImage.setImageResource(R.drawable.tictac);
            }
        }

        return v;
    }
}
