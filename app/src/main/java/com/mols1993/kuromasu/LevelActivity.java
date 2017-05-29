package com.mols1993.kuromasu;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Vector;

public class LevelActivity extends AppCompatActivity {

    int boardSize = 0;
    Board board = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String level = extras.getString("level");

        Vector<String> tablero = new Vector<String>();

        if(level.equals("save.txt")){
            try{
                String ret = "";
                File file = new File(getFilesDir(), level);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = reader.readLine()) != null) {
                    if (!receiveString.equals("")) {
                        String[] r = receiveString.split(" ");
                        boardSize = r.length;
                        for (int i = 0; i < boardSize; i++) {
                            tablero.add(r[i]);
                        }
                        stringBuilder.append(r[0]);
                    }
                }
                board = new Board(boardSize, tablero, this);
                drawBoard(board);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        else {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(level)));
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = reader.readLine()) != null) {
                    if (!receiveString.equals("")) {
                        String[] r = receiveString.split(" ");
                        boardSize = r.length;
                        for (int i = 0; i < boardSize; i++) {
                            tablero.add(r[i]);
                        }
                        stringBuilder.append(r[0]);
                    }
                }
                board = new Board(boardSize, tablero, this);
                drawBoard(board);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setStatusText(String text){
        TextView txt = (TextView) findViewById(R.id.txtStatus);
        txt.setText(text);
    }

    public void onClick(View v){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("save.txt", Context.MODE_PRIVATE));
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    int state = board.getTile(i, j).getState();
                    int num = board.getTile(i, j).getNumber();
                    String toWrite = "";
                    if(num > 0){
                        toWrite = Integer.toString(num) + " ";
                    }
                    else if(num == 0 && state == 1){
                        toWrite = "B ";
                    }
                    else if(state == 0){
                        toWrite = ". ";
                    }
                    else if(state == -1){
                        toWrite = "N ";
                    }
                    Log.i("Write", toWrite);
                    outputStreamWriter.write(toWrite);
                }
                outputStreamWriter.write("\n\r");
            }
            outputStreamWriter.write("\n\r");
            Tile[][] solution = board.getSolution();
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    int state = solution[i][j].getState();
                    int num = solution[i][j].getNumber();
                    String toWrite = "";
                    if(num > 0){
                        toWrite = Integer.toString(num) + " ";
                    }
                    else if(num == 0 && state == 1){
                        toWrite = "B ";
                    }
                    else if(state == 0){
                        toWrite = ". ";
                    }
                    else if(state == -1){
                        toWrite = "N ";
                    }
                    Log.i("Write", toWrite);
                    outputStreamWriter.write(toWrite);
                }
                outputStreamWriter.write("\n\r");
            }
            outputStreamWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawBoard(Board board){
        int bSize = board.getSize();
        LinearLayout baseLayout = (LinearLayout) findViewById(R.id.mainLayout);
        baseLayout.removeAllViews();
        for(int i = 0; i < bSize; i++){
            LinearLayout ll = new LinearLayout(this);
            for(int j = 0; j < bSize; j++){
                ll.addView(board.getTile(i, j));
                Log.i("Tile", String.valueOf(board.getTile(i, j)));
            }
            baseLayout.addView(ll);
        }
    }
}
