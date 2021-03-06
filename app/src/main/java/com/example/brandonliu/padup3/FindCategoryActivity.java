package com.example.brandonliu.padup3;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class FindCategoryActivity extends AppCompatActivity {

    private ArrayList<Category> availableContent;       //used for our listview
    private ArrayList<String> categoryList;
    private ArrayAdapter<String> adapter;               //adapter for listview
    private ListView listView;
    public final static double TIME_TO_DELETE = 3.6*1000000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_category);
        Firebase.setAndroidContext(this);

        //firebase
        Firebase ref = new Firebase("https://radiant-inferno-9080.firebaseio.com/inputs");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryList.clear(); // Clear list before updating
                //loop through database
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Input info = child.getValue(Input.class);

                    String cat = (String) child.child("category").getValue();
                    double ts = (double) System.currentTimeMillis();
                    //less than, that means this value is recent enough to keep.
                    if (ts - info.getTimestamp() < TIME_TO_DELETE) {
                        Log.d("not too old", String.valueOf(ts - info.getTimestamp()));
                        //add if doesnt contain
                        if (!categoryList.contains(cat)) {
                            categoryList.add(cat);
                        }
                    } else {
                        //too old, delete
                        Log.d("way too old", String.valueOf(ts - info.getTimestamp()));
                        //child.getRef() is the key (key generated by timestamp), set value to null is the same as delete
                        child.getRef().setValue(null);
                    }
                    //Log.d("categories", cat);
                }

                //if there's no room hosted, make a toast.
                if (categoryList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "No rooms are currently being hosted.", Toast.LENGTH_SHORT).show();
                }
                adapter = new ArrayAdapter<String>(FindCategoryActivity.this, R.layout.list_layout, categoryList);

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        String categoryClicked = categoryList.get(position);
                        Intent intent = new Intent(FindCategoryActivity.this, FindDungeonActivity.class);
                        //send the category string
                        intent.putExtra("category", categoryClicked);
                        startActivity(intent);
                    }
                });
            }

            public void onCancelled(FirebaseError error) {
            }
        });

        //HTTPget arrayList of categories
        availableContent = new ArrayList<Category>();
        categoryList = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.listView);
    }
}
