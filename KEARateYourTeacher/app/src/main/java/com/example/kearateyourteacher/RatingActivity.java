package com.example.kearateyourteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kearateyourteacher.inputFilter.MinMaxInputFilter;
import com.example.kearateyourteacher.model.CourseRating;

import java.util.ArrayList;

public class RatingActivity extends AppCompatActivity {
    final String COURSE_RATING_KEY = "COURSE_RATING_KEY";

    TextView header;
    Button submit;
    EditText a1, a2, a3, a4, a5, a6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        init();

        Intent intent = getIntent();
        final CourseRating courseRating = intent.getParcelableExtra(COURSE_RATING_KEY);
        header = findViewById(R.id.header);
        header.setText(courseRating.getCourseName());

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> ratings = new ArrayList<>();
                ratings.add(Integer.parseInt(a1.getText().toString()));
                ratings.add(Integer.parseInt(a2.getText().toString()));
                ratings.add(Integer.parseInt(a3.getText().toString()));
                ratings.add(Integer.parseInt(a4.getText().toString()));
                ratings.add(Integer.parseInt(a5.getText().toString()));
                ratings.add(Integer.parseInt(a6.getText().toString()));
                courseRating.calculateFinalRating(ratings);
                Log.d("myerror", courseRating.getFinalRating().toString());

                Intent newIntent = new Intent(getApplicationContext(), ResultActivity.class);
                newIntent.putExtra(COURSE_RATING_KEY, courseRating);
                startActivity(newIntent);
            }
        });
    }

    private void init(){
        header = findViewById(R.id.header);
        submit = findViewById(R.id.submit);
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        a4 = findViewById(R.id.a4);
        a5 = findViewById(R.id.a5);
        a6 = findViewById(R.id.a6);

        a1.setFilters(new InputFilter[]{new MinMaxInputFilter(1, 100)});
        a2.setFilters(new InputFilter[]{new MinMaxInputFilter(1, 100)});
        a3.setFilters(new InputFilter[]{new MinMaxInputFilter(1, 100)});
        a4.setFilters(new InputFilter[]{new MinMaxInputFilter(1, 100)});
        a5.setFilters(new InputFilter[]{new MinMaxInputFilter(1, 100)});
            a6.setFilters(new InputFilter[]{new MinMaxInputFilter(1, 100)});

    }
}
