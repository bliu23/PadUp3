package com.example.brandonliu.padup3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class FindDungeonActivity extends AppCompatActivity {

    private ArrayList<String> dungeonList;
    private ArrayAdapter<String> adapter;               //adapter for listview
    private ArrayList<Input> roomList;
    private ListView listView;
    private Category cat;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_dungeon);
        Firebase.setAndroidContext(this);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
//        cat = intent.getParcelableExtra("categoryobj");
//        cat.print();

        //firebase
        Firebase ref = new Firebase("https://radiant-inferno-9080.firebaseio.com/inputs");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                dungeonList.clear(); // Clear list before updating
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    //get all dungeons of that specific category clicked earlier
                    if(((String)child.child("category").getValue()).equals(category)) {
                        String addDun = (String) child.child("dungeonName").getValue();
                        //only add if not present
                        if(!dungeonList.contains(addDun)) {
                            dungeonList.add(addDun);
                        }
                        //Log.d("dungeons", addDun);
                    }

                }

                adapter = new ArrayAdapter<String>(FindDungeonActivity.this, R.layout.list_layout, dungeonList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        String dungeonClicked = dungeonList.get(position);
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Input info = child.getValue(Input.class);
                            String dun = info.getDungeonName();
                            //only get the rooms of this particular dungeon
                            if (dun.equals(dungeonClicked)) {
                                //add if doesnt contain
                                roomList.add(info);
                            }//endif
                        }
                        //testprint
//                        for(int i = 0; i < roomList.size(); i++) {
//                            roomList.get(i).print();
//                        }

                        Intent intent = new Intent(FindDungeonActivity.this, DisplayRoomActivity.class);
                        intent.putExtra("input", roomList);
 //                       Log.d("dungeonClicked", dungeonClicked);
                        // We will probably want to send the entire category in this case.
//                        intent.putExtra("categoryobj", availableContent.get(position));
                        startActivity(intent);
                    }
                });
            }
            public void onCancelled(FirebaseError error) { }
        });


        roomList = new ArrayList<Input>();
        dungeonList = new ArrayList<String>();
        listView = (ListView)findViewById(R.id.listView);

        //display categories
//        adapter = new ArrayAdapter<String>(this, R.layout.list_layout, dungeonList);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                String dungeonClicked = dungeonList.get(position);
//                Log.d("cat clicked", dungeonClicked);
//                Intent intent = new Intent(FindDungeonActivity.this, DisplayRoomActivity.class);
//                startActivity(intent);
//                //Send the dungeonClicked to the next activity. In that activity, make an HTTP POST
//                //request to retrieve a list of rooms for that specific dungeon.
//            }
//        });
    }
}
