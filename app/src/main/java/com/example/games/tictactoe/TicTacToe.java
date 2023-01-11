package com.example.games.tictactoe;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.games.Menu;
import com.example.games.command.ConnectToSession;
import com.example.games.command.CreateSession;
import com.example.games.command.Login;
import com.example.games.command.LookSession;
import com.example.games.command.Register;
import com.example.games.command.SendGameData;
import com.example.games.InputThread;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class TicTacToe extends Thread{
    public static Class[] classes = {Turn.class, Restart.class, Boolean.class};
    public int cells[][];
    public Pos posCells[][];
    public boolean isPlayerX;
    int stepsToWin;
    public Set<Pos> winSet;
    public int myId=-1;
    public int activePlayerId;
    boolean firstTurnIsX = false;
    Gson gson;
    View view;
    public int filledCells;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public TicTacToe(View view) throws IOException {
        this.view = view;
        gson = new Gson();
        newGame(myId,3,3);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        super.run();
//        try {
//            Socket socket = new Socket("192.168.1.34", 8000);
//            out = new OutputStreamWriter(socket.getOutputStream());
////            InputThread inputThread = new InputThread(this, new BufferedReader(
////                    new InputStreamReader(socket.getInputStream())),view );
////            inputThread.run();
////            newGame(3,3);
//            //inputThread.join();
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void newGame(int initiatorId, int w, int h) throws IOException {
        if(initiatorId==0) {
            cells = new int[h][w];
            posCells = new Pos[h][w];
            winSet = null;
            stepsToWin = 3;
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    posCells[i][j] = new Pos(i, j);
                }
            }
            isPlayerX = true;
            //if(myId==1) myId=2; else myId=1;
            filledCells = 0;
        }
    }
    private Set<Pos> calcWinSet(int im, int jm){
        int checkLabel = isPlayerX ? 1 : 2;
        Set<Pos> horizontal = new HashSet<>();
        horizontal.addAll(directCheck(im,jm,1,0, checkLabel));
        horizontal.addAll(directCheck(im,jm,-1,0, checkLabel));
        if(horizontal.size() >= stepsToWin) return horizontal;

        Set<Pos> vertical = new HashSet<>();
        vertical.addAll(directCheck(im,jm,0, 1, checkLabel));
        vertical.addAll(directCheck(im,jm,0,-1, checkLabel));
        if(vertical.size() >= stepsToWin) return vertical;

        Set<Pos> diag = new HashSet<>();
        diag.addAll(directCheck(im,jm,1, 1, checkLabel));
        diag.addAll(directCheck(im,jm,-1,-1, checkLabel));
        if(diag.size() >= stepsToWin) return diag;

        Set<Pos> diagAlt = new HashSet<>();;
        diagAlt.addAll(directCheck(im,jm,1, -1, checkLabel));
        diagAlt.addAll(directCheck(im,jm,-1,1, checkLabel));
        if(diagAlt.size() >= stepsToWin) return diagAlt;

        return null;

    }
    private Set<Pos> directCheck(int i, int j, int iDirect, int jDirect, int checkLabel){
        Set<Pos> positions = new HashSet<>();
        while(cells[i][j]==checkLabel){
            positions.add(posCells[i][j]);
            i+=iDirect;
            j+=jDirect;
            if(i>= cells.length || i<0 || j>=cells[0].length || j < 0) break;
        }
        return positions;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void makeTurn(Turn turn) throws IOException {
       // turn.playerId == activePlayerId &&
        if( turn.playerId == activePlayerId && winSet == null && cells[turn.position.i][turn.position.j]==0){

            if(turn.playerId == myId){
                new SendGameData(gson.toJson(turn), Turn.class.getSimpleName()).send(Menu.out);
            }
            if(isPlayerX) cells[turn.position.i][turn.position.j]=1; else cells[turn.position.i][turn.position.j]=2;
            filledCells++;
            Set<Pos> tempWinSet = calcWinSet(turn.position.i, turn.position.j);
            if(tempWinSet!=null) {
                winSet = tempWinSet;
            }
            isPlayerX = !isPlayerX;
            if(winSet!=null && turn.playerId == myId){
                Menu.ticTacToeActivity.win();
            }
            if(filledCells==9&&winSet==null){
                newGame(0, 3, 3);
            }
            if(activePlayerId == 0){
                activePlayerId = 1;
            }else{
                activePlayerId = 0;
            }
        }
    }


}
