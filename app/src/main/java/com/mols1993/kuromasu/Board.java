package com.mols1993.kuromasu;

import android.content.Context;
import android.util.Log;

import java.util.Vector;

/**
 * Created by cypher on 5/23/17.
 */

public class Board {
    Tile[][] board;
    Vector<Tile> fixedTiles;
    int boardSize;
    public Board(int size, Context context) {
        boardSize = size;
        board = new Tile[boardSize][boardSize];
        fixedTiles = new Vector<Tile>();
        //Fill board
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                board[i][j] = new Tile(j, this, i, j, context);
                if(board[i][j].getNumber() != 0){
                    fixedTiles.add(board[i][j]);
                }
            }
        }
    }

    public int getColor(int i, int j){
        return board[i][j].getState();
    }

    public void checkReglas(){
        //TO-DO
    }

    public String regla1(){
        String msje = "";
        for (Tile x : fixedTiles){
            if(x.getBlancas() != x.getNumber()){
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
            conectadas = root.getConexo(root, 0);
        }

        if(blancas != conectadas){
            msje = "Existen fichas blancas desconectadas";
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

    public int getSize(){
        return boardSize;
    }
}