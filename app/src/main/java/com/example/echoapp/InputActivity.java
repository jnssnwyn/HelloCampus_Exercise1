package com.example.echoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {

    // Log tag used for Logcat debugging
    private static final String TAG = "InputActivityLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);


        Log.d(TAG, "InputActivity onCreate started");


        EditText editMessage = findViewById(R.id.editMessage);
        Button btnSend = findViewById(R.id.btnSend);


        btnSend.setOnClickListener(v -> {

            Log.d(TAG, "Send button clicked");


            String message = editMessage.getText().toString();


            Intent intent = new Intent(InputActivity.this, EchoActivity.class);


            intent.putExtra("MESSAGE_KEY", message);


            startActivity(intent);
        });
    }
}