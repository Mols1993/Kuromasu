package com.mols1993.kuromasu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

public class LevelActivity extends AppCompatActivity {

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
        int boardSize = 0;
        Board board = null;

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(level)));
            String receiveString;
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = reader.readLine()) != null){
                if(!receiveString.equals("")){
                    String[] r = receiveString.split(" ");
                    boardSize = r.length;
                    for(int i = 0; i < boardSize; i++){
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
