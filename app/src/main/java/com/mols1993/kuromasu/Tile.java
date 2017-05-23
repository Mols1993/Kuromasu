package com.mols1993.kuromasu;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by mols1 on 5/22/2017.
 */

public class Tile extends android.support.v7.widget.AppCompatImageButton{
    private int state = 0, number = 0, x = 0, y = 0;
    boolean contado = false;
    private Tile top, bot, left, right;
    Board board;
    public Tile(int n, Board b, int x, int y, Context context){
        super(context);
        number = n;
        board = b;
        this.x = x;
        this.y = y;
        if(n > 0){
            state = 1;
        }
        setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN && number == 0) {
                    state++;
                    if(state > 1){
                        state = -1;
                        updateColor();
                        board.checkReglas();
                    }
                }
                return true;
            }
        });
    }

    /**
     *
     * @param a Top link
     * @param b Right link
     * @param c Bot link
     * @param d Left link
     */
    public void setLinks(Tile a, Tile b, Tile c, Tile d){
        top = a;
        right = b;
        bot = c;
        left = d;
    }

    public void showLinks(){
        Log.i("TopLink", Integer.toString(top.getNumber()));
        Log.i("RightLink", Integer.toString(right.getNumber()));
        Log.i("BotLink", Integer.toString(bot.getNumber()));
        Log.i("LeftLink", Integer.toString(left.getNumber()));
    }

    public int getBlancas(){
        int count = 0;

        //Contar hacia arriba
        for(int j = y; j >= 0; j--){
            if(board.getColor(x, j) == 1){
                count++;
            }
            else{
                break;
            }
        }
        //Contar hacia abajo
        for(int j = y; j < board.getSize(); j++){
            if(board.getColor(x, j) == 1){
                count++;
            }
            else{
                break;
            }
        }
        //Contar hacia la derecha
        for(int i = x; i < board.getSize(); i++){
            if(board.getColor(i, y) == 1){
                count++;
            }
            else{
                break;
            }
        }
        //Contar hacia la izquierda
        for(int i = x; i >= 0; i--){
            if(board.getColor(i, y) == 1){
                count++;
            }
            else{
                break;
            }
        }
        return count;
    }

    public boolean getNegras(){
        if(top != null){
            if(top.getState() == -1){
                return true;
            }
        }
        if(right != null){
            if(right.getState() == -1){
                return true;
            }
        }
        if(bot != null){
            if(bot.getState() == -1){
                return true;
            }
        }
        if(left != null){
            if(left.getState() == -1){
                return true;
            }
        }
        return false;
    }

    public int getConexo(Tile tile, int counter){
        counter++;
        contado = true;
        if(top != null && top.getState() == 1 && !top.counted()){
            counter = getConexo(top, counter);
        }
        if(right != null && right.getState() == 1 && !right.counted()){
            counter = getConexo(right, counter++);
        }
        if(bot != null && bot.getState() == 1 && !bot.counted()){
            counter = getConexo(bot, counter++);
        }
        if(left != null && left.getState() == 1 && !left.counted()){
            counter = getConexo(left, counter++);
        }
        return counter;
    }

    private void updateColor() {
        //TO-DO
    }

    public boolean counted(){
        return contado;
    }

    public String getCoords(){
        return "[" + x + ", " + y + "]";
    }

    public int getNumber(){
        return number;
    }

    public int getState(){
        return state;
    }
}
