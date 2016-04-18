package com.example.brandonliu.padup3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;

public class DisplayRoomActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> rooms;
    private ArrayAdapter<String> adapter;               //adapter for listview
    private ArrayList<Input> roomList;
    private String dungeon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_room);
        Firebase.setAndroidContext(this);

        roomList = getIntent().getParcelableArrayListExtra("input");

        listView = (ListView) findViewById(R.id.listView);
        rooms = new ArrayList<String>();

        ImageAdapter imageAdapter = new ImageAdapter(this, roomList);
        listView.setAdapter(imageAdapter);

        //firebase
        Firebase ref = new Firebase("https://radiant-inferno-9080.firebaseio.com/inputs");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rooms.clear(); // Clear list before updating
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    //String cat = (String) child.child("roomId").getValue();
                    Input info = child.getValue(Input.class);
                    String dun = info.getDungeonName();
                    //only get the rooms of this particular dungeon
                    if (dun.equals(dungeon)) {
                        //add if doesnt contain
                        rooms.add("Room ID: " + info.getRoomId());
                        roomList.add(info);
                        //Log.d("categories", rooms.get(rooms.size() - 1));
                    }//endif
                }
            }

            public void onCancelled(FirebaseError error) {
            }
        });

    }

}
