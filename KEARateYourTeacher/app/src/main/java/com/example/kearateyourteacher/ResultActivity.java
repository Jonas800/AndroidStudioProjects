package com.example.kearateyourteacher;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kearateyourteacher.model.CourseRating;

import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {
    final String COURSE_RATING_KEY = "COURSE_RATING_KEY";
    TextView finalRatingView, couseNameView;
    Button submit;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init();

        final Resources resources = getResources();
        HashMap<String, Drawable> imageMap = new HashMap<String, Drawable>(){{
            put("A", ResourcesCompat.getDrawable(resources, R.drawable.mewtwo, null));
            put("B", ResourcesCompat.getDrawable(resources, R.drawable.mew, null));
            put("C", ResourcesCompat.getDrawable(resources, R.drawable.charizard, null));
            put("D", ResourcesCompat.getDrawable(resources, R.drawable.charmeleon, null));
            put("E", ResourcesCompat.getDrawable(resources, R.drawable.charmander, null));
            put("Get a new job!", ResourcesCompat.getDrawable(resources, R.drawable.magikarp, null));
        }};

        CourseRating courseRating = getIntent().getParcelableExtra(COURSE_RATING_KEY);
        couseNameView.setText(getResources().getString(R.string.resultCourseName, courseRating.getCourseName()));
        finalRatingView.setText(getResources().getString(R.string.resultFinalRating, String.format("%.1f", courseRating.getFinalRating().doubleValue())));

        imageView.setImageDrawable(imageMap.get(courseRating.getGrade()));

        final String emailBody = "Course name: "
                + courseRating.getCourseName()
                + "\n\rFinal rating: "
                + String.format("%.1f", courseRating.getFinalRating().doubleValue())
                + "\n\rGrade: "
                + courseRating.getGrade();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jonasolsenit@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "KEA Course Rating");
                intent.putExtra(Intent.EXTRA_TEXT, emailBody);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });
    }

    private void init(){
        couseNameView = findViewById(R.id.courseName);
        finalRatingView = findViewById(R.id.finalRating);
        submit = findViewById(R.id.submit);
        imageView = findViewById(R.id.imageView);
    }
}
