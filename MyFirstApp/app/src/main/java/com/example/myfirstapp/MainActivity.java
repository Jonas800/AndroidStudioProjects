package com.example.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            EditText editText = findViewById(R.id.editText2);
            TextView textView = findViewById(R.id.textView);
            @Override
            public void onClick(View v) {
                String text = "Hello " + editText.getText().toString();
                textView.setText(text);
            }
        });
    }
}
