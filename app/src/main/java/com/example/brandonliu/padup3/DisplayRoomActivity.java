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
    private ArrayList<String> image1List;
    private ArrayList<String> image2List;
    private ArrayList<String> image3List;
    private ArrayList<String> image4List;
    private ArrayList<String> image5List;

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_room);
        Firebase.setAndroidContext(this);

        dungeon = getIntent().getStringExtra("dungeon");
        Log.d("dungeon", dungeon);

        listView = (ListView) findViewById(R.id.listView);
        rooms = new ArrayList<String>();
        roomList = new ArrayList<Input>();
        //list of images for our display
        image1List = new ArrayList<String>();
        image2List = new ArrayList<String>();
        image3List = new ArrayList<String>();
        image4List = new ArrayList<String>();
        image5List = new ArrayList<String>();

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
                        Log.d("categories", rooms.get(rooms.size() - 1));
                        //also add images to our array lists
                        //super dirty but messy but keep this functionality for now.
                        image1List.add(info.getImgs()[0]);
                        image2List.add(info.getImgs()[1]);
                        image3List.add(info.getImgs()[2]);
                        image4List.add(info.getImgs()[3]);
                        image5List.add(info.getImgs()[4]);

//                        int imgid = R.id.image1;
//                        for(int i = 0; i < info.getImgs().length; i++) {
//
//                            new DownloadImageTask((ImageView) findViewById(imgid)).execute(info.getImgs()[i]);
//                            imgid++;
//                        }
                    }//endif
                }//endfor
                for(int i = 0; i < rooms.size(); i++) {
                    Log.d("image1", image1List.get(i));
                    Log.d("image2", image2List.get(i));
                    Log.d("image3", image3List.get(i));
                    Log.d("image4", image4List.get(i));
                    Log.d("image5", image5List.get(i));
                }

                adapter = new ArrayAdapter<String>(DisplayRoomActivity.this, R.layout.list_layout, rooms);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        String roomId = rooms.get(position);
                        roomList.get(position).print();
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
