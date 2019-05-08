package com.example.degreeconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String STATE_RESULT = "result";
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            result = savedInstanceState.getString(STATE_RESULT);
        } else {
            result = "0";
        }

        final EditText editText = findViewById(R.id.editText);
        final TextView textView = findViewById(R.id.textView);

        textView.setText(result);

        Button toF = findViewById(R.id.toF);
        toF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double c = Double.parseDouble(editText.getText().toString());
                Double f = (c * 9/5) + 32;
                //(32°C × 9/5) + 32
                result = f.toString();
                editText.setText(result);
                textView.setText(result);
            }
        });

        Button toC = findViewById(R.id.toC);
        toC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double f = Double.parseDouble(editText.getText().toString());
                Double c = (f - 32) * 5/9;
                //(32°F − 32) × 5/9 = 0°C
                result = c.toString();
                editText.setText(result);
                textView.setText(result);
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(STATE_RESULT, result);

        super.onSaveInstanceState(savedInstanceState);
    }
}
