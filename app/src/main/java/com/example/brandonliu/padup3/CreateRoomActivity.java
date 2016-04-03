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

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText editRoomId;
    private EditText[] editMonsters;
    private Button button;
    private String dungeon;
    private String category;

    private String htmlURL = "http://www.puzzledragonx.com/en/monsterbook.asp";
    private Document htmlDoc;

    private ArrayList<String> monsterImgs;
    private ArrayList<String> monsterNames;
    private ArrayAdapter<String> autoAdapter;
    private AutoCompleteTextView autoText;

    private static final String[] test = {"Verdandi", "Ares", "Sonia", "test", "Verdoondi", "verdaaaandi", "Verdverd", "VerDanDi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        dungeon = getIntent().getStringExtra("dungeon");
        category = getIntent().getStringExtra("category");

        monsterImgs = new ArrayList<String>();
        monsterNames = new ArrayList<String>();

        button = (Button)findViewById(R.id.requestButton);
        editRoomId = (EditText)findViewById(R.id.roomId);
        editMonsters = new EditText[6];
        autoText = (AutoCompleteTextView)findViewById(R.id.test);
        //set edit text fields.
        for(int i = 0; i < 6; i++) {
            editMonsters[i] = (EditText)findViewById(R.id.leaderText + i);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomId = editRoomId.getText().toString();
                String[] monsterInputs = new String[6];
                if(filled()) {
                    String temp = convertArrayToJson(roomId, monsterInputs);
                    Log.d("json", temp);
                }
            }
        });

        JsoupAsyncTask task = new JsoupAsyncTask();
        //execute task
        task.execute();

        autoAdapter = new ArrayAdapter<String>(this, R.layout.autocomplete_layout, R.id.textView, monsterNames);
    }

    String convertArrayToJson(String roomId, String[] str) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("roomId", roomId);
            obj.put("category", category);
            obj.put("dungeon", dungeon);
            for(int i = 0; i < str.length; i++) {
                String temp = "monster" + String.valueOf(i);
                obj.put(temp, str[i]);
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
        String[] monsterInputs = new String[6];
        for(int i = 0; i < 6; i++) {
            //get input fields
            monsterInputs[i] = editMonsters[i].getText().toString();
            //if an input field is empty, it's not filled.
            if(monsterInputs[i].equals("")) {
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
            autoText.setAdapter(autoAdapter);

        }
    }
}
