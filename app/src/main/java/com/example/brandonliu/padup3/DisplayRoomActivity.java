package com.example.brandonliu.padup3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class DisplayRoomActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> rooms;
    private ArrayAdapter<String> adapter;               //adapter for listview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_room);
        Firebase.setAndroidContext(this);
        String dungeon = getIntent().getStringExtra("dungeon");
        Log.d("dungeon", dungeon);

        listView = (ListView)findViewById(R.id.listView);
        rooms = new ArrayList<String>();

        //firebase
        Firebase ref = new Firebase("https://radiant-inferno-9080.firebaseio.com/inputs");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rooms.clear(); // Clear list before updating
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String cat = (String) child.child("roomId").getValue();
                    //add if doesnt contain
                    if (!rooms.contains(cat)) {
                        rooms.add(cat);
                    }
                    Log.d("categories", cat);
                }

                adapter = new ArrayAdapter<String>(DisplayRoomActivity.this, R.layout.list_layout, rooms);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        String roomId = rooms.get(position);
                        Intent intent = new Intent(DisplayRoomActivity.this, DisplayRoomActivity.class);
                        intent.putExtra("roomId", roomId);
                        Log.d("roomId", roomId);
                        // We will probably want to send the entire category in this case.
//                        intent.putExtra("categoryobj", availableContent.get(position));
                        //startActivity(intent);
                    }
                });
            }

            public void onCancelled(FirebaseError error) {
            }
        });

    }
}
