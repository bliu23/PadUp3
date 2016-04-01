package com.example.brandonliu.padup3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class JoinRoomActivity extends AppCompatActivity {

    private ArrayList<Category> contentAvailable;       //used for our listview
    private ArrayAdapter<String> adapter;               //adapter for listview
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_room);

        //HTTPget arrayList of categories

        //display categories

        //display dungeons in category available

        //display room numbers + teams + stuff available.

    }
}
