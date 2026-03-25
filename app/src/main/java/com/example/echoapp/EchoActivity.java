package com.example.echoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EchoActivity extends AppCompatActivity {

    private static final String TAG = "EchoActivityLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echo);


        Log.d(TAG, "EchoActivity onCreate started");


        TextView txtMessage = findViewById(R.id.txtMessage);


        Intent intent = getIntent();


        String message = intent.getStringExtra("MESSAGE_KEY");

        Log.d(TAG, "Received message: " + message);


        if (message != null) {
            txtMessage.setText(message);
        } else {
            txtMessage.setText("No message received");
        }
    }
}