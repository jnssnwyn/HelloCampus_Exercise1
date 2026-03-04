package com.example.hellocampus;  // keep your own package name

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int mCounter = 0;
    private TextView counterDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("ID: 16400069");  // PUT YOUR REAL STUDENT ID
        }


        TextView departmentText = findViewById(R.id.tvDepartment);
        Button changeButton = findViewById(R.id.btnChangeName);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departmentText.setText("Dr. Name of Department Chairwoman");
            }
        });


        counterDisplay = findViewById(R.id.tvCounter);
        Button increaseButton = findViewById(R.id.btnIncrease);

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounter++;
                counterDisplay.setText(String.valueOf(mCounter));
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("COUNT_KEY", mCounter);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCounter = savedInstanceState.getInt("COUNT_KEY");
        counterDisplay.setText(String.valueOf(mCounter));
    }
}
