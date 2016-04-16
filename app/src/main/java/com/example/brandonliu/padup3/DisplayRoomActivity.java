package com.example.brandonliu.padup3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayRoomActivity extends AppCompatActivity {

    private ListView listView;
    private String[] testData = {"case 1", "case2", "case3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_room);

        listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_layout, testData);
        listView.setAdapter(adapter);

    }
}
