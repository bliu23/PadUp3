package com.example.brandonliu.padup3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SelectDungeon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dungeon);
        Intent intent = getIntent();
        //int intValue = intent.getIntExtra("dungeonCatID", 0);
        String stringValue = intent.getExtras().getString("dungeonCatID");
//        Log.d("test intent", String.valueOf(intValue));
        Log.d("test intent", stringValue);
    }
}
