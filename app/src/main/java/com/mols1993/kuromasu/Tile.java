package com.mols1993.kuromasu;

import android.content.Context;
import android.graphics.Color;
import android.print.PrintAttributes;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by mols1 on 5/22/2017.
 */

public class Tile extends android.support.v7.widget.AppCompatButton{
    private int state = 0, number = 0, x = 0, y = 0, size = 0;
    public boolean contado = false;
    private Tile top, bot, left, right;
    Board board;

    /**
     *
     * @param n Number on tile
     * @param b Board containing the tile
     * @param x X position in board
     * @param y Y position in board
     * @param context IDK
     */
    public Tile(int n, Board b, int x, int y, Context context, int size){
        super(context);
        number = n;
        board = b;
        this.x = x;
        this.y = y;
        this.size = size;
        if((x + y)%2 == 0) {
            setBackgroundColor(Color.parseColor("#777777"));
        }
        else {
            setBackgroundColor(Color.parseColor("#878787"));
        }
        if(number > 0){
            state = 1;
            setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        setText(String.valueOf(number));
        if(number == 0){
            setText(" ");
        }
        setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN && number == 0) {
                    state++;
                    Log.i("State", String.valueOf(state));
                    if(state > 1){
                        state = -1;
                    }
                    updateColor();
                    board.checkReglas();
                }
                return true;
            }
        });
        setWidth(size);
        setMinimumWidth(size);
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
                Log.i("Contar", String.valueOf(board.getTile(x, j).getCoords()));
                count++;
            }
            else{
                break;
            }
        }
        //Contar hacia abajo
        for(int j = y; j < board.getSize(); j++){
            if(board.getColor(x, j) == 1){
                Log.i("Contar", String.valueOf(board.getTile(x, j).getCoords()));
                count++;
            }
            else{
                break;
            }
        }
        //Contar hacia la derecha
        for(int i = x; i < board.getSize(); i++){
            if(board.getColor(i, y) == 1){
                Log.i("Contar", String.valueOf(board.getTile(i, y).getCoords()));
                count++;
            }
            else{
                break;
            }
        }
        //Contar hacia la izquierda
        for(int i = x; i >= 0; i--){
            if(board.getColor(i, y) == 1){
                Log.i("Contar", String.valueOf(board.getTile(i, y).getCoords()));
                count++;
            }
            else{
                break;
            }
        }
        count = count -3;
        Log.i("Contar", String.valueOf(count));
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

    public int getConexo(int counter){
        counter++;
        contado = true;
        String s = "[" + x + ", " + y + "]";
        Log.i("Conexo", s);
        if(top != null && top.getState() == 1 && !top.counted()){
            Log.i("ConexoT", top.getCoords());
            counter = top.getConexo(counter);
        }
        if(right != null && right.getState() == 1 && !right.counted()){
            Log.i("ConexoR", right.getCoords());
            counter = right.getConexo(counter);
        }
        if(bot != null && bot.getState() == 1 && !bot.counted()){
            Log.i("ConexoB", bot.getCoords());
            counter = bot.getConexo(counter);
        }
        if(left != null && left.getState() == 1 && !left.counted()){
            Log.i("ConexoL", left.getCoords());
            counter = left.getConexo(counter);
        }
        return counter;
    }

    private void updateColor() {
        if(state == -1){
            setBackgroundColor(Color.parseColor("#000000"));
        }
        else if(state == 0){
            if((x + y)%2 == 0) {
                setBackgroundColor(Color.parseColor("#777777"));
            }
            else {
                setBackgroundColor(Color.parseColor("#878787"));
            }
        }
        else if(state == 1){
            setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
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
