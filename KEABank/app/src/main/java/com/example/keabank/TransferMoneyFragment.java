package com.example.keabank;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import models.Account;
import models.Client;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransferMoneyFragment extends Fragment {
    final String CLIENT_KEY = "CLIENT_KEY", ACCOUNT_KEY = "ACCOUNT_KEY";
    EditText amount, accountNumberTo;
    Button transfer;
    Spinner spinner;
    Account account;
    Client client;
    Context context;
    Resources resources;
    View rootView;


    public TransferMoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_transfer_money, container, false);
        init();
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItemId() != 0) {
                    if (!TextUtils.isEmpty(amount.getText()) || !TextUtils.isEmpty(accountNumberTo.getText())) {
                        Double amountDouble = Double.valueOf(amount.getText().toString());
                        String otherAccountNumber = accountNumberTo.getText().toString();

                        try {
                            doTransaction(amountDouble, otherAccountNumber);
                        } catch (IllegalArgumentException ex) {
                            makeErrorToast(R.string.transfer_pension_age_error);
                        }

                    } else {
                        makeErrorToast(R.string.transfer_text_error_multiple);
                    }
                } else {
                    makeErrorToast(R.string.transfer_spinner_error);
                }
            }
        });
        return rootView;
    }

    private void doTransaction(Double amountDouble, String otherAccountNumber) {
        String selectedAccount = spinner.getSelectedItem().toString();
        for (Account accountToTransferFrom : client.getAccounts()) {
            if (resources.getString(accountToTransferFrom.getAccountType()).equals(selectedAccount)) {
                try {
                    Client otherClient = Client.getOtherClientDummyData();
                    boolean isCorrectOtherAccountNumber = false;
                    Account otherAcc = null;
                    for (Account otherAccount : otherClient.getAccounts()) {
                        if (otherAccount.getAccountNumber().equals(otherAccountNumber)) {
                            isCorrectOtherAccountNumber = true;
                            otherAcc = otherAccount;
                        }
                    }
                    //Do the transaction if the receiving account was found
                    if (isCorrectOtherAccountNumber) {
                        accountToTransferFrom.withdraw(amountDouble, client);
                        otherAcc.deposit(amountDouble, client);
                        Log.d("mybp", "doTransaction: " + otherClient.toString());
                        break;
                    } else {
                        makeErrorToast(R.string.transfer_non_existing_account_error);
                    }
                } catch (IllegalArgumentException ex) {
                    makeErrorToast(R.string.transfer_pension_age_error);
                }
            }
        }
    }

    private void makeOverdraftConfirmDialog(final Double amountDouble, final String otherAccountNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.overdraft_confirm_dialog_message);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTransaction(amountDouble, otherAccountNumber);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void makeErrorToast(int errorMessage) {
        Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void createAccountSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item);
        adapter.add(resources.getString(R.string.spinner_default_value));
        for (Account account : client.getAccounts()) {
            adapter.add(resources.getString(account.getAccountType()));
        }
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void init() {
        transfer = rootView.findViewById(R.id.submit);
        amount = rootView.findViewById(R.id.amount);
        accountNumberTo = rootView.findViewById(R.id.toAccount);
        Bundle bundle = getArguments();
        client = bundle.getParcelable(CLIENT_KEY);
        account = bundle.getParcelable(ACCOUNT_KEY);
        spinner = rootView.findViewById(R.id.spinner);
        context = TransferMoneyFragment.this.getContext();
        resources = getResources();
        createAccountSpinner();
    }

}
