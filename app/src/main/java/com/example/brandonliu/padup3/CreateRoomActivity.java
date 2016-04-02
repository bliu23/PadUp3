package com.example.brandonliu.padup3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText editRoomId;
    private EditText[] editMonsters;
    private Button button;
    private String dungeon;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        dungeon = getIntent().getStringExtra("dungeon");
        category = getIntent().getStringExtra("category");
        Log.d("dungeon", dungeon);
        Log.d("category", category);
        button = (Button)findViewById(R.id.requestButton);
        editRoomId = (EditText)findViewById(R.id.roomId);
        editMonsters = new EditText[6];
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
}
