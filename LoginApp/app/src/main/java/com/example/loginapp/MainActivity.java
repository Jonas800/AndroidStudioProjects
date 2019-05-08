package com.example.loginapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText pw, email;
    Button buttonLogin;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String PASSWORD = "passwordKey";
    public static final String EMAIL = "emailKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = email.getText().toString();
                String pwStr = pw.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(EMAIL, emailStr);
                editor.putString(PASSWORD, pwStr);
                editor.commit();
            }
        });
    }

    private void init(){
        pw = findViewById(R.id.password);
        email = findViewById(R.id.email);
        buttonLogin = findViewById(R.id.loginButton);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        email.setText(sharedPreferences.getString(EMAIL, ""));
        pw.setText(sharedPreferences.getString(PASSWORD, ""));
    }
}
