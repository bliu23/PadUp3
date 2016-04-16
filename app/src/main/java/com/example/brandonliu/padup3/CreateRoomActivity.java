package com.example.brandonliu.padup3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class CreateRoomActivity extends AppCompatActivity {

    public static final int MONSTERS_PER_TEAM = 6;

    private EditText editRoomId;
    private AutoCompleteTextView[] selectMonsters;
    private Button button;
    private String dungeon;
    private String category;

    private String htmlURL = "http://www.puzzledragonx.com/en/monsterbook.asp";
    private Document htmlDoc;

    private ArrayList<String> monsterImgs;
    private ArrayList<String> monsterNames;
    private ArrayAdapter<String> autoAdapter;

    private String[] monsterInputs;
    private String[] imgInputs;
    private Input input;


    private Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        //firebase setup
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://radiant-inferno-9080.firebaseio.com/");

        //get intent
        category = getIntent().getStringExtra("category");
        dungeon = getIntent().getStringExtra("dungeon");

        //declare input
        input = new Input(category, dungeon);

        //declare arraylists used for img and names
        monsterImgs = new ArrayList<String>();
        monsterNames = new ArrayList<String>();

        //display vars
        button = (Button)findViewById(R.id.requestButton);
        editRoomId = (EditText)findViewById(R.id.roomId);
        selectMonsters = new AutoCompleteTextView[MONSTERS_PER_TEAM];

        //inputs we get from filled out fields
        imgInputs = new String[MONSTERS_PER_TEAM];
        monsterInputs = new String[MONSTERS_PER_TEAM];

        //set edit text fields.
        for(int i = 0; i < MONSTERS_PER_TEAM; i++) {
            //set autocompletetextview
            selectMonsters[i] = (AutoCompleteTextView)findViewById(R.id.leaderText + i);
            final int k = i;
            //when selecting from autocompletetextview, grabs the image of the monster.
            selectMonsters[i].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                    //index is the index of the entire arrayList of monsters/images. NOT index in the autocomplete choice
                    int index = monsterNames.indexOf(selectMonsters[k].getText().toString());
                    imgInputs[k] = monsterImgs.get(index);
                }
            });
        }
        //on click, checks to see if all boxes are filled. If so,
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomId = editRoomId.getText().toString();
                if(filled()) {
                    for(int i = 0; i < MONSTERS_PER_TEAM; i++) {
                        monsterInputs[i] = selectMonsters[i].getText().toString();
                        if(imgInputs[i] == null) {
                            imgInputs[i] = "--";
                        }
                    }
                    input.setRoomId(roomId);
                    input.setImgs(imgInputs);
                    input.setMonsters(monsterInputs);
                    /*
                    String jString = convertArrayToJson(roomId, imgInputs, monsterInputs);
                    Log.d("json", jString);*/

                    Firebase fieldRef = ref.child("inputs");
                    fieldRef.push().setValue(input);
                }
            }
        });

        JsoupAsyncTask task = new JsoupAsyncTask();
        //execute task
        task.execute();

        autoAdapter = new ArrayAdapter<String>(this, R.layout.autocomplete_layout, R.id.textView, monsterNames);
    }

    /*
    * room id, category, dungeon
    * monster0 img0   monster1 img1   -> monster n img n
    * if there is a custom input, the img will be --.
    * */
    String convertArrayToJson(String roomId, String[] imgs, String[] names) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("roomId", roomId);
            obj.put("category", category);
            obj.put("dungeon", dungeon);
            for(int i = 0; i < names.length; i++) {
                String tempname = "monster" + String.valueOf(i);
                String tempimg = "img" + String.valueOf(i);
                obj.put(tempname, names[i]);
                obj.put(tempimg, imgs[i]);
            }
            return obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
    * This function checks if the editText fields are completed or not.
    * */
    private boolean filled() {
        String roomId = editRoomId.getText().toString();
        for(int i = 0; i < MONSTERS_PER_TEAM; i++) {
            //if an input field is empty, it's not filled.
            if(selectMonsters[i].getText().toString().equals("")) {
                Log.d("fill", "empty");
                return false;
            }
        }
        //check room id, must be 8 numbers long
        if(roomId.length() != 8) {
            return false;
        }
        return true;
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                htmlDoc = Jsoup.connect(htmlURL).get();
                Elements monsterList = htmlDoc.select("a[href^=monster.asp?n]");
                Log.d("test", String.valueOf(monsterList.size()));

                for(Element itr : monsterList) {
                    monsterImgs.add(itr.select("img").attr("abs:data-original"));
                    monsterNames.add(itr.select("img").attr("title"));
                }
                Log.d("sizebefore", String.valueOf(monsterNames.size()));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            for(int i = 0; i < MONSTERS_PER_TEAM; i++) {
                selectMonsters[i].setAdapter(autoAdapter);
            }

        }
    }
}
