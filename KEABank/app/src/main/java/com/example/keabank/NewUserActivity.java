package com.example.keabank;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.Affiliate;
import models.BudgetAccount;
import models.Client;
import models.DefaultAccount;

public class NewUserActivity extends AppCompatActivity {

    EditText name, email, password, city, address;
    DatePicker dateOfBirth;
    Button submit;
    FirebaseAuth firebaseAuth;
    String TAG = "mybp";
    private final String CLIENT_KEY = "CLIENT_KEY";

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
                            Client client = createClient();
                            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                            intent.putExtra(CLIENT_KEY, client);
                            startActivity(intent);
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
        dateOfBirth = findViewById(R.id.birthdate);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        submit = findViewById(R.id.submit);

        dateOfBirth.setMaxDate(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    private Client createClient(){
        Client client = new Client();
        ArrayList<Account> accounts = new ArrayList<>();
        DefaultAccount defaultAccount = new DefaultAccount();
        defaultAccount.setBalance(new Double(1000));
        BudgetAccount budgetAccount = new BudgetAccount();
        budgetAccount.setBalance(new Double(1000));
        accounts.add(defaultAccount);
        accounts.add(budgetAccount);

        client.setAccounts(accounts);
        client.setCity(city.getText().toString());
        client.setAddress(address.getText().toString());
        client.setEmail(email.getText().toString());
        LocalDate localDate = LocalDate.of(dateOfBirth.getYear(), dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
        client.setDateOfBirth(localDate);

        Geocoder geocoder = new Geocoder(getApplicationContext());
        try{
            List<Address> addresses = geocoder.getFromLocationName(client.getAddress() + " " + client.getCity(), 1);
            Affiliate affiliate = Affiliate.getClosestAffiliate(addresses.get(0), getApplicationContext());
            client.setAffiliate(affiliate);
            Log.d(TAG, "createClient: " + addresses.get(0).toString());
        } catch (IOException ex){
            client.setAffiliate(Affiliate.getCopenhagenAffiliate());
        }

        return client;
    }
}
