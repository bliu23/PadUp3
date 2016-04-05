package com.example.brandonliu.padup3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FindDungeonActivity extends AppCompatActivity {

    private ArrayList<String> dungeonList;
    private ArrayAdapter<String> adapter;               //adapter for listview
    private ListView listView;
    private Category cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_dungeon);

        Intent intent = getIntent();
        cat = intent.getParcelableExtra("categoryobj");
        cat.print();

        dungeonList = cat.getDungeons();
        listView = (ListView)findViewById(R.id.listView);

        //display categories
        adapter = new ArrayAdapter<String>(this, R.layout.list_layout, dungeonList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String dungeonClicked = dungeonList.get(position);
                Log.d("cat clicked", dungeonClicked);

                //Send the dungeonClicked to the next activity. In that activity, make an HTTP POST
                //request to retrieve a list of rooms for that specific dungeon.
            }
        });
    }
}
