package com.example.games;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.games.command.SessionInfo;
import com.example.games.command.UserInfo;

import java.util.LinkedList;

public class UserInfoAdapter extends BaseAdapter {
    Context context;
    int resourceLayout;
    LinkedList<UserInfo> users;
    UserInfoAdapter(Context context, int resourceLayout, LinkedList<UserInfo> users) {
        this.context = context;
        this.users = users;
        this.resourceLayout = resourceLayout;
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
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

        UserInfo p = (UserInfo) getItem(i);

        if (p != null) {
            TextView nickname = (TextView) v.findViewById(R.id.nickname);
            TextView chess_wins = (TextView) v.findViewById(R.id.chess_wins);
            TextView tictac_wins = (TextView) v.findViewById(R.id.tictac_wins);

            nickname.setText(p.getLogin());
            chess_wins.setText(String.valueOf(p.getInfo().get("chess")));
            tictac_wins.setText(String.valueOf(p.getInfo().get("tictac")));
        }

        return v;
    }
}
