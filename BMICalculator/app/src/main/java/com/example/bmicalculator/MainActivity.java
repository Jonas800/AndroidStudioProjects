package com.example.bmicalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String STATE_BMI = "result";
    private String bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText weightView = findViewById(R.id.weight);
        final EditText heightView = findViewById(R.id.height);
        //final Toast toast = new Toast(getApplicationContext());
        // toast.setText(bmi); //this crashes everything

        if (savedInstanceState != null) {
            bmi = savedInstanceState.getString(STATE_BMI);
            //toast.setText(bmi);
        } else {
            bmi = "0";
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double weight = Double.parseDouble(weightView.getText().toString());
                Double height = Double.parseDouble(heightView.getText().toString());
                //BMI = kg/m^2
                Double bmiTemp = weight / Math.pow((height / 100), 2);
                bmi = bmiTemp.toString();
                Toast toast = Toast.makeText(getApplicationContext(), bmi, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(STATE_BMI, bmi);

        super.onSaveInstanceState(savedInstanceState);
    }
}
