package com.example.keabank;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import models.Account;
import models.Client;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    final String CLIENT_KEY = "CLIENT_KEY", ACCOUNT_KEY = "ACCOUNT_KEY";

    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        Client client = bundle.getParcelable(CLIENT_KEY);
        //final Account account = bundle.getParcelable(ACCOUNT_KEY);
        createTable(rootView, client);

        return rootView;
    }

    private void createTable(View rootView, Client client){
        TableLayout tableLayout = rootView.findViewById(R.id.overviewTable);
        tableLayout.setStretchAllColumns(true);
        Context context = OverviewFragment.this.getContext();

        for(Account account : client.getAccounts()){
            TableRow tableRow = new TableRow(context);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(lp);

            TextView labelAccountType = new TextView(context);
            labelAccountType.setText(account.getAccountType());
            TextView labelBalance = new TextView(context);
            labelBalance.setText(String.format("%.2f", account.getBalance()));
            labelBalance.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

            tableRow.addView(labelAccountType);
            tableRow.addView(labelBalance);
            tableLayout.addView(tableRow);
        }

    }

}
