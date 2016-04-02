package com.example.brandonliu.padup3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FindCategoryActivity extends AppCompatActivity {

    private ArrayList<Category> availableContent;       //used for our listview
    private ArrayList<String> categoryList;
    private ArrayAdapter<String> adapter;               //adapter for listview
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_category);

        //HTTPget arrayList of categories
        availableContent = new ArrayList<Category>();
        categoryList = new ArrayList<String>();
        listView = (ListView)findViewById(R.id.listView);
        prepareTestData();


        //display categories
        adapter = new ArrayAdapter<String>(this, R.layout.list_layout, categoryList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String categoryClicked = categoryList.get(position);
                Intent intent = new Intent(FindCategoryActivity.this, FindDungeonActivity.class);

                // We will probably want to send the entire category in this case.
                intent.putExtra("categoryobj", availableContent.get(position));
                startActivity(intent);
            }
        });

        //display dungeons in category available

        //display room numbers + teams + stuff available.

    }
    private void prepareTestData() {
        availableContent.add(new Category("testcat1"));
        availableContent.add(new Category("testcat2"));
        availableContent.add(new Category("last test cat"));

        availableContent.get(0).addDungeon("Cat 1 Dungeon 1");
        availableContent.get(0).addDungeon("Cat 1 Dungeon 2");
        availableContent.get(0).addDungeon("Cat 1 Dungeon 3");
        availableContent.get(0).addDungeon("Cat 1 Dungeon 4");

        availableContent.get(1).addDungeon("Cat 2 Dungeon 1");
        availableContent.get(1).addDungeon("Cat 2 Dungeon 2");
        availableContent.get(1).addDungeon("Cat 2 Dungeon 3");

        availableContent.get(2).addDungeon("Cat 3 Dungeon 1");
        availableContent.get(2).addDungeon("Cat 3 Dungeon 2");
        availableContent.get(2).addDungeon("Cat 3 Dungeon 3");
        availableContent.get(2).addDungeon("Cat 3 Dungeon 4");
        availableContent.get(2).addDungeon("Cat 3 Dungeon 5");
        availableContent.get(2).addDungeon("Cat 3 Dungeon 6");

        categoryList.add(availableContent.get(0).getCat());
        categoryList.add(availableContent.get(1).getCat());
        categoryList.add(availableContent.get(2).getCat());
    }
}
