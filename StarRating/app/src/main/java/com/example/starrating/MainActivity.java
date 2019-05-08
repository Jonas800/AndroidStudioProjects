package com.example.starrating;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createRadioButtons();
    }
    private void createRadioButtons() {
        RadioGroup rg = findViewById(R.id.radioGroup);
        rg.setOrientation(RadioGroup.VERTICAL);
        RadioGroup rg2 = findViewById(R.id.radiogroup2);
        for(int i=1; i<=5; i++){
            RadioButton rb  = new RadioButton(this);
            rb.setText("" + i);
            rb.setId(i);
            rg.addView(rb);
        }
        for(int i=6; i<=10; i++){
            RadioButton rb  = new RadioButton(this);
            rb.setText("" + i);
            rb.setId(i);
            rg2.addView(rb);
        }


    }
}
