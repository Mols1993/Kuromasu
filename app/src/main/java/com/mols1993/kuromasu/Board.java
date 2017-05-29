package com.mols1993.kuromasu;

import android.content.Context;
import android.graphics.Point;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Vector;

/**
 * Created by cypher on 5/23/17.
 */

public class Board {
    Tile[][] board, solution;
    Vector<Tile> fixedTiles;
    int boardSize;
    Vector<String> tablero;
    LevelActivity context;
    public Board(int size, Vector<String> tablero, LevelActivity context) {
        boardSize = size;
        this.tablero = tablero;
        this.context = context;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point s = new Point();
        display.getSize(s);
        int width = s.x;
        int height = s.y;
        Log.i("Width", String.valueOf(width));

        board = new Tile[boardSize][boardSize];
        solution = new Tile[boardSize][boardSize];

        fixedTiles = new Vector<Tile>();
        //Fill board
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                int value = 0;
                if(!Objects.equals(tablero.get(i * boardSize + j), ".")){
                    Log.i("Tablero", String.valueOf(tablero.get(i*boardSize + j)));
                    //value = Integer.parseInt(tablero.get(i * boardSize + j));
                    String val = tablero.get(i * boardSize + j);
                    if(val.equals("N")){
                        value = 0;
                        board[i][j] = new Tile(value, this, i, j, context, width/boardSize);
                        board[i][j].setState(-1);
                    }
                    else if(val.equals("B")){
                        value = 0;
                        board[i][j] = new Tile(value, this, i, j, context, width/boardSize);
                        board[i][j].setState(1);
                    }
                    else{
                        value = Integer.parseInt(tablero.get(i * boardSize + j));
                        board[i][j] = new Tile(value, this, i, j, context, width/boardSize);
                    }
                }
                else{
                    board[i][j] = new Tile(value, this, i, j, context, width/boardSize);
                }
                if(board[i][j].getNumber() != 0){
                    fixedTiles.add(board[i][j]);
                }
            }
        }
        for(int i = 0; i < (boardSize * boardSize); i++){
            tablero.remove(0);
        }

        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                int value = 0;
                if(tablero.get(i * boardSize + j).equals("B")){
                    solution[i][j] = new Tile(value, this, i, j, context, width/boardSize);
                    solution[i][j].setState(1);
                }
                else if(tablero.get(i * boardSize + j).equals("N")){
                    solution[i][j] = new Tile(value, this, i, j, context, width/boardSize);
                    solution[i][j].setState(-1);
                }
                else{
                    value = Integer.parseInt(tablero.get(i * boardSize + j));
                    solution[i][j] = new Tile(value, this, i, j, context, width/boardSize);
                }
            }
        }

        //Connect board
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                Tile top = null, right = null, bot = null, left = null;
                if(i == 0){
                    top = null;
                    bot = board[i+1][j];
                }
                else if(i < (boardSize - 1)){
                    top = board[i-1][j];
                    bot = board[i+1][j];
                }
                else if(i == (boardSize - 1)){
                    top = board[i-1][j];
                    bot = null;
                }
                if(j == 0){
                    left = null;
                    right = board[i][j+1];
                }
                else if(j < (boardSize - 1)){
                    left = board[i][j-1];
                    right = board[i][j+1];
                }
                else if(j == (boardSize - 1)){
                    left = board[i][j-1];
                    right = null;
                }
                board[i][j].setLinks(top, right, bot, left);
            }
        }
    }

    public int getColor(int i, int j){
        return board[i][j].getState();
    }

    public boolean checkReglas(){
        context.setStatusText("");
        if(winCondition()){
            context.setStatusText("A WINNER IS YOU");
            //Toast.makeText(context, "A WINRAR IS YOU", Toast.LENGTH_SHORT).show();
            return true;
        }
        String error1 = regla1();
        String error3 = regla3();
        String error4 = regla4();
        if(!error1.equals("")){
            context.setStatusText(error1);
            //Toast.makeText(context, error1, Toast.LENGTH_SHORT).show();
        }
        else if(!error4.equals("")){
            context.setStatusText(error4);
            //Toast.makeText(context, error4, Toast.LENGTH_SHORT).show();
        }
        else if(!error3.equals("")){
            context.setStatusText(error3);
            //Toast.makeText(context, error3, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean winCondition(){
        boolean ret = false;
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(!(board[i][j].getState() == solution[i][j].getState())){
                    return false;
                }
            }
        }
        return true;
    }

    public String regla1(){
        String msje = "";
        for (Tile x : fixedTiles){
            if(x.getBlancas() > x.getNumber()){
                msje = "Problema en" + x.getCoords();
            }
        }
        return msje;
    }

    public String regla3(){
        Tile root = null;
        String msje = "";
        int blancas = 0, conectadas = 0;
        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){
                if(board[x][y].getState() == 1){
                    root = board[x][y];
                    blancas++;
                }
            }
        }
        if(root != null) {
            conectadas = root.getConexo(0);
        }

        if(blancas != conectadas){
            msje = "Existen fichas blancas desconectadas";
        }

        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){
                board[x][y].contado = false;
            }
        }

        return msje;
    }

    public String regla4(){
        String msje = "";
        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){
                if(board[x][y].getState() == -1){
                    if(board[x][y].getNegras()){
                        msje = "Hay 2 fichas negras adyacentes en [" + x + ", " + y + "]";
                    }
                }
            }
        }
        return msje;
    }

    public Tile[][] getSolution(){
        return solution;
    }

    public int getSize(){
        return boardSize;
    }

    public Tile getTile(int x, int y){
        return board[x][y];
    }
}
