package com.example.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button[] buttons = new Button[15];
    Button equal, delete;
    TextView result;
    final String RESULT_KEY = "RESULT_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        final String resultStr = getIntent().getStringExtra(RESULT_KEY);
        result.setText(resultStr);

        for (final Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    result.setText(result.getText().toString() + button.getText().toString());
                }
            });
        }
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultString = result.getText().toString();
                resultString = resultString.replace('x', '*');
                try {
                    Expression expression = new Expression(resultString);
                    BigDecimal bigDecimal = expression.eval();
                    result.setText(bigDecimal.toString());

                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra(RESULT_KEY, bigDecimal.toString());
                    startActivity(intent);
                } catch(Expression.ExpressionException eex){
                    Toast toast=Toast.makeText(getApplicationContext(),"Badly formed input",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String initialString = result.getText().toString();
                int length = initialString.length();
                if (length > 0) {
                    result.setText(initialString.substring(0, length - 1));
                }
            }
        });
    }

    private void init() {
        buttons[0] = findViewById(R.id.zero);
        buttons[1] = findViewById(R.id.one);
        buttons[2] = findViewById(R.id.two);
        buttons[3] = findViewById(R.id.three);
        buttons[4] = findViewById(R.id.four);
        buttons[5] = findViewById(R.id.five);
        buttons[6] = findViewById(R.id.six);
        buttons[7] = findViewById(R.id.seven);
        buttons[8] = findViewById(R.id.eight);
        buttons[9] = findViewById(R.id.nine);
        buttons[10] = findViewById(R.id.separator);
        buttons[11] = findViewById(R.id.plus);
        buttons[12] = findViewById(R.id.minus);
        buttons[13] = findViewById(R.id.divide);
        buttons[14] = findViewById(R.id.multiply);

        delete = findViewById(R.id.delete);
        equal = findViewById(R.id.equal);
        result = findViewById(R.id.result);
    }
}
