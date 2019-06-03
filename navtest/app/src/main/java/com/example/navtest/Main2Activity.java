package com.example.navtest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class Main2Activity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Toolbar tb = findViewById(R.id.toolbar);
        //tb.setTitle(getResources().getString(R.string.title_activity_account_overview));
        getSupportActionBar().setTitle("whatever");
        View contentView = inflater.inflate(R.layout.activity_main2, null, false);
        drawer.addView(contentView);
    }
}
