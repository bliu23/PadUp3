package com.example.brandonliu.padup3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class CreateRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        EditText editText = (EditText)findViewById(R.id.editText);

        String roomId = editText.getText().toString();

        Toast.makeText(CreateRoomActivity.this, roomId, Toast.LENGTH_LONG).show();
    }
}
