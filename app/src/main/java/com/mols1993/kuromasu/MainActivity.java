package com.mols1993.kuromasu;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Vector<String> tablero = new Vector<String>();
        int boardSize = 0;
        Board board = null;

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("Kuromasu9x9_01.txt")));
            String receiveString;
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = reader.readLine()) != null){
                if(receiveString != ""){
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Board finalBoard = board;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, finalBoard.regla3(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
