package com.example.kearateyourteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kearateyourteacher.model.CourseRating;

public class MainActivity extends AppCompatActivity {
    final String COURSE_RATING_KEY = "COURSE_RATING_KEY";
    final Button[] buttons = new Button[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        for (final Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CourseRating courseRating = new CourseRating(button.getText().toString(), new Double(0));
                    Log.d("myerror", courseRating.getCourseName());
                    Intent intent = new Intent(MainActivity.this, RatingActivity.class);
                    intent.putExtra(COURSE_RATING_KEY, courseRating);
                    startActivity(intent);
                }
            });
        }
    }

    private void init() {
        buttons[0] = findViewById(R.id.button);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);
    }
}