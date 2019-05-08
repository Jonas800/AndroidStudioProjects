package com.example.currencyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText amount = findViewById(R.id.amount);
        final TextView output = findViewById(R.id.output);

        ArrayList<MyCurrency> myCurrencies = new ArrayList<>();
        //DKK ? 1. eu = 0,13, US =  0,15, gdp = 0,12
        myCurrencies.add(new MyCurrency("DKK", new Double(1)));
        myCurrencies.add(new MyCurrency("EUR", new Double(7.46)));
        myCurrencies.add(new MyCurrency("USD", new Double(6.64)));
        myCurrencies.add(new MyCurrency("GDP", new Double(8.63)));

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<MyCurrency> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, myCurrencies);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        final Spinner spinnerFrom = (Spinner) findViewById(R.id.spinnerFrom);
        spinnerFrom.setAdapter(adapter);
        final Spinner spinnerTo = (Spinner) findViewById(R.id.spinnerTo);
        spinnerTo.setAdapter(adapter);

/*
        final MyCurrency currencyFrom = getMyCurrencyFromSpinner(spinnerFrom);
        final MyCurrency currencyTo = getMyCurrencyFromSpinner(spinnerTo);
*/
        final MyCurrency currencyFrom = new MyCurrency();
        final MyCurrency currencyTo = new MyCurrency();

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    Double newAmount = MyCurrency.convertCurrency(currencyFrom, currencyTo, new Double(amount.getText().toString()));
                    output.setText(String.format("%.2f", newAmount.floatValue()));
                }
            }
        });

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyCurrency temp = (MyCurrency) parent.getSelectedItem();
                currencyFrom.setCountry(temp.getCountry());
                currencyFrom.setRate(temp.getRate());
                if (amount.length() > 0) {
                    Double newAmount = MyCurrency.convertCurrency(currencyFrom, currencyTo, new Double(amount.getText().toString()));
                    output.setText(String.format("%.2f", newAmount.floatValue()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MyCurrency temp = (MyCurrency) parent.getSelectedItem();
                currencyFrom.setCountry(temp.getCountry());
                currencyFrom.setRate(temp.getRate());
            }
        });
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyCurrency temp = (MyCurrency) parent.getSelectedItem();
                currencyTo.setCountry(temp.getCountry());
                currencyTo.setRate(temp.getRate());
                if (amount.length() > 0) {
                    Double newAmount = MyCurrency.convertCurrency(currencyFrom, currencyTo, new Double(amount.getText().toString()));
                    output.setText(String.format("%.2f", newAmount.floatValue()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MyCurrency temp = (MyCurrency) parent.getSelectedItem();
                currencyTo.setCountry(temp.getCountry());
                currencyTo.setRate(temp.getRate());
            }
        });
    }

    /*public static MyCurrency getMyCurrencyFromSpinner(Spinner spinner) {
        final MyCurrency myCurrency = new MyCurrency();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyCurrency temp = (MyCurrency) parent.getSelectedItem();
                myCurrency.setCountry(temp.getCountry());
                myCurrency.setRate(temp.getRate());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MyCurrency temp = (MyCurrency) parent.getSelectedItem();
                myCurrency.setCountry(temp.getCountry());
                myCurrency.setRate(temp.getRate());
            }
        });

        return myCurrency;
    }*/
}
