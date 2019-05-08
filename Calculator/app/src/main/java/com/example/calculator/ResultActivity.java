package com.example.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

public class ResultActivity extends AppCompatActivity {
    final String RESULT_KEY = "RESULT_KEY";
    TextView resultView;
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init();

        final String result = getIntent().getStringExtra(RESULT_KEY);
        resultView.setText(result);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(RESULT_KEY, result);
                startActivity(intent);
            }
        });
    }

    private void init(){
        resultView = findViewById(R.id.result);
        returnButton = findViewById(R.id.returnHome);
    }
}
