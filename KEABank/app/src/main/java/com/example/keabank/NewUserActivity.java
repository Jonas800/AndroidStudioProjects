package com.example.keabank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class NewUserActivity extends AppCompatActivity {

    EditText name, email, password;
    Button submit;
    FirebaseAuth firebaseAuth;
    String TAG = "MYLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        init();

        firebaseAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_str = email.getText().toString();
                String password_str = password.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(email_str, password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "New User/ onComplete: Success");
                            startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                            finish();
                        } else {
                            Log.d(TAG, "New User/ onComplete: Failed");
                            Toast.makeText(getApplicationContext(), R.string.new_user_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void init() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
    }
}
