package com.example.keabank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;

import models.Client;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    String TAG = "mybp";
    EditText email, password;
    Button button;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String PASSWORD = "passwordKey";
    public static final String EMAIL = "emailKey";
    private final String CLIENT_KEY = "CLIENT_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_str = email.getText().toString();
                String password_str = password.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(EMAIL, email_str);
                editor.putString(PASSWORD, password_str);
                editor.commit();

                firebaseAuth.signInWithEmailAndPassword(email_str, password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Login/ onComplete: Success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                            Client client = Client.getDummyData();
                            intent.putExtra(CLIENT_KEY, client);
                            Geocoder geocoder = new Geocoder(getApplicationContext());
                            try{
                                List<Address> addresses = geocoder.getFromLocationName(client.getAddress() + " " + client.getCity(), 1);
                                Log.d(TAG, "createClient: " + addresses.get(0).toString());
                            } catch (IOException ex){
                                //set default affiliate
                            }
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "Login/ onComplete: Failed");
                            Toast.makeText(getApplicationContext(), R.string.login_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private void init(){
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button = findViewById(R.id.button);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        email.setText(sharedPreferences.getString(EMAIL, ""));
        password.setText(sharedPreferences.getString(PASSWORD, ""));
    }
}
