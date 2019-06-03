package com.example.keabank;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.BudgetAccount;
import models.Client;
import models.DefaultAccount;
import models.Transaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultAccountFragment extends Fragment {

    final String CLIENT_KEY = "CLIENT_KEY", ACCOUNT_KEY = "ACCOUNT_KEY";
    EditText amount;
    Button withdraw, deposit;

    public DefaultAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_default_account, container, false);

        Bundle bundle = getArguments();
        final Client client = bundle.getParcelable(CLIENT_KEY);
        final Account account = bundle.getParcelable(ACCOUNT_KEY);
        init(rootView);

        createTransactionTable(rootView, client);

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amountDouble = new Double(amount.getText().toString());

                account.withdraw(amountDouble, client);
                createTransactionTable(rootView, client);
            }
        });

        return rootView;
    }

    private void createTransactionTable(View rootView, Client client){
        TableLayout tableLayout = rootView.findViewById(R.id.transactionHolder);
        tableLayout.removeAllViews();
        tableLayout.setStretchAllColumns(true);
        Context context = DefaultAccountFragment.this.getContext();

        List<Account> accounts = new ArrayList<>();

        for(Account account : client.getAccounts()){
            if(account instanceof DefaultAccount){
                accounts.add(account);
            }
        }
        TextView balance = rootView.findViewById(R.id.balance_amount);
        balance.setText(accounts.get(0).getBalance().toString());

        for(Transaction transaction : accounts.get(0).getTransactions()){
            TableRow tableRow = new TableRow(context);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(lp);

            TextView labelTimestamp = new TextView(context);
            labelTimestamp.setText(transaction.getTimestamp().toString());

            TextView labelClient = new TextView(context);
            labelClient.setText(transaction.getClient().getName());

            TextView labelAmount = new TextView(context);
            labelAmount.setText(transaction.getAmount().toString());
            labelAmount.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

            tableRow.addView(labelTimestamp);
            tableRow.addView(labelClient);
            tableRow.addView(labelAmount);
            tableLayout.addView(tableRow);
        }
    }

    private void init(View rootView){
        amount = rootView.findViewById(R.id.amount);
        withdraw = rootView.findViewById(R.id.withdraw);
        deposit = rootView.findViewById(R.id.deposit);
    }
}
