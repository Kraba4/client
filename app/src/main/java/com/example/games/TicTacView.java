package com.example.games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.games.command.SendGameData;
import com.example.games.tictactoe.Pos;
import com.example.games.tictactoe.Restart;
import com.example.games.tictactoe.TicTacToe;
import com.example.games.tictactoe.Turn;
import com.google.gson.Gson;

import java.io.IOException;


public class TicTacView extends View {
    int darkColor;
    int brightColor;
    int winColor;
    int cellSize;
    Paint paintDark;Paint paintBright;Paint alertText;Paint paintWin;
    TicTacToe game;
    TicTacToeActivity parent;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public TicTacView(Context context) throws IOException {
        super(context);
        game = new TicTacToe(this);
       // game.start();
        darkColor = getResources().getColor(R.color.teal_700);
        brightColor = getResources().getColor(R.color.purple_200);

        paintDark = new Paint();
        paintDark.setColor(darkColor);
        paintDark.setStrokeWidth(8);
        paintDark.setStyle(Paint.Style.STROKE);

        paintBright = new Paint();
        paintBright.setColor(brightColor);

        alertText = new Paint();
        alertText.setAntiAlias(true);
        alertText.setColor(getResources().getColor(R.color.black));
        alertText.setStyle(Paint.Style.FILL);
        alertText.setTextSize(50);
        alertText.setStrokeWidth(10);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TicTacView(Context context, @Nullable AttributeSet attrs) throws IOException {
        super(context, attrs);
        darkColor = getResources().getColor(R.color.teal_700);
        brightColor = getResources().getColor(R.color.purple_200);
        winColor = getResources().getColor(R.color.purple_500);
        paintDark = new Paint();
        paintDark.setColor(darkColor);
        paintDark.setStrokeWidth(16);
        paintDark.setStyle(Paint.Style.STROKE);
        paintDark.setAntiAlias(true);

        paintWin = new Paint();
        paintWin.setColor(winColor);
        paintWin.setStrokeWidth(16);
        paintWin.setStyle(Paint.Style.STROKE);
        paintWin.setAntiAlias(true);

        paintBright = new Paint();
        paintBright.setColor(brightColor);

        alertText = new Paint();
        alertText.setAntiAlias(true);
        alertText.setColor(getResources().getColor(R.color.purple_200));
        alertText.setStyle(Paint.Style.FILL);
        alertText.setTextSize(50);
        alertText.setStrokeWidth(10);
        game = new TicTacToe(this);

//        game.start();
//        game.cells = new int[3][3];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cellSize = getWidth() / game.cells[0].length;;
        int offset = 5;
        float offsetX = (float) (0.25 * cellSize);
        Paint drawPaint;
        for(int i=0;i<game.cells.length;i++){
            for(int j=0;j<game.cells[0].length;j++){
                canvas.drawRect(j* cellSize + offset, i* cellSize + offset,
                        (j+1)* cellSize - offset, (i+1)* cellSize - offset, paintBright );
                if(game.winSet != null && game.winSet.contains(game.posCells[i][j])){
                    drawPaint = paintWin;
                }else{
                    drawPaint = paintDark;
                }
                switch (game.cells[i][j]){
                    case 1:
                        canvas.drawLine(j*cellSize + offsetX, i*cellSize + offsetX,
                                (j+1)* cellSize - offsetX, (i+1)* cellSize - offsetX, drawPaint);
                        canvas.drawLine((j+1)*cellSize - offsetX, i*cellSize + offsetX,
                                (j)* cellSize + offsetX, (i+1)* cellSize - offsetX, drawPaint);
                        break;
                    case 2:
                        canvas.drawCircle(j*cellSize + cellSize/2.0F, i*cellSize + cellSize/2.0F, (float) (0.25*cellSize),
                                drawPaint);
                        break;
                }
            }
        }
        if(game.myId == 0) {
            if (game.isPlayerX) {
                canvas.drawText("Мой ход", 10, game.cells.length * cellSize + 60, alertText);
            } else {
                canvas.drawText("Ход противника", 10, game.cells.length * cellSize + 60, alertText);
            }
        }else {
            if (game.isPlayerX) {
                canvas.drawText("Ход противника", 10, game.cells.length * cellSize + 60, alertText);
            } else {
                canvas.drawText("Мой ход", 10, game.cells.length * cellSize + 60, alertText);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int jm = ((int)x)/cellSize;
        int im = ((int)y)/cellSize;
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(im>=0&&im < game.cells.length &&jm>=0&& jm < game.cells[0].length){
                try {
                    game.makeTurn(new Turn(game.myId, new Pos(im,jm)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        invalidate();

        return super.onTouchEvent(event);
    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void restart() throws IOException {
//        game.newGame(game.myId, 3,3);
//        Gson gson = new Gson();
//        new SendGameData(gson.toJson(new Restart(game.myId)), Restart.class.getSimpleName()).send(game.out);
//        invalidate();
//    }



}
