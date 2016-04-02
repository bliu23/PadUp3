package com.example.brandonliu.padup3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CreateDungeonActivity extends AppCompatActivity {

    private ListView listView;
    private Category receivedCategory;
    private ArrayList<String> dungeons;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dungeon);

        Intent intent = getIntent();
        receivedCategory = intent.getExtras().getParcelable("cat");

        dungeons = new ArrayList<String>();
        listView = (ListView)findViewById(R.id.listView);

        prepareAdapter(receivedCategory);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String dungeonClicked = dungeons.get(position);
                Intent intent = new Intent(CreateDungeonActivity.this, CreateRoomActivity.class);
                intent.putExtra("dungeon", dungeonClicked);
                intent.putExtra("category", receivedCategory.getCat());
                startActivity(intent);
            }
        });
    }

    //adds dungeons from category class to array list, then set adapter. used to populate listview.
    private void prepareAdapter(Category receivedCategory) {
        for(int i = 0; i < receivedCategory.getDungeons().size(); i++) {
            dungeons.add(receivedCategory.getDungeons().get(i));
        }
        adapter = new ArrayAdapter<String>(this, R.layout.list_layout, dungeons);
        listView.setAdapter(adapter);
    }

}
