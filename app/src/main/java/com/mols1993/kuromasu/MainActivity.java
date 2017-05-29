package com.mols1993.kuromasu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClick(View v){
        Intent intent = new Intent(this, LevelSelect.class);
        startActivity(intent);
    }

    public void toInstructions(View v){
        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);
    }
}
