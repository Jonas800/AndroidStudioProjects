package com.example.kglbconverter;

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

        final EditText editText = findViewById(R.id.editText);
        final TextView textView = findViewById(R.id.textView);

        if(savedInstanceState != null){
            result = savedInstanceState.getString(STATE_RESULT);
        } else{
            result = "0";
        }

        textView.setText(result);

        Button toLbButton = findViewById(R.id.lb);
        toLbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double kg = Double.parseDouble(editText.getText().toString());
                Double lb = kg * 2.2046;
                editText.setText(lb.toString());
                textView.setText(lb.toString());
                result = lb.toString();
            }
        });

        Button toKgButton = findViewById(R.id.kg);
        toKgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText editText = findViewById(R.id.editText);
                Double lb = Double.parseDouble(editText.getText().toString());
                Double kg = lb * (1/2.2046);
                editText.setText(kg.toString());
                textView.setText(kg.toString());
                result = kg.toString();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(STATE_RESULT, result);

        super.onSaveInstanceState(savedInstanceState);
    }
}
