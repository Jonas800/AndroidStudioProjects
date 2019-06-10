package com.example.keabank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import java.util.Random;

import helpers.RandomAccountNumber;
import models.Account;
import models.Client;

public class CreateAccountDialogFragment extends DialogFragment {

    final String ACCOUNT_TYPE = "ACCOUNT_TYPE", CLIENT_KEY = "CLIENT_KEY", NEW_ACCOUNT_KEY = "NEW_ACCOUNT_KEY";
    Client client;
    Account account;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //int accountType = getArguments().getParcelable(ACCOUNT_TYPE);
        client = getArguments().getParcelable(CLIENT_KEY);
        account = getArguments().getParcelable(NEW_ACCOUNT_KEY);
        account.setAccountNumber(RandomAccountNumber.getRandomAccountNumber());
        Log.d("mybp", "onCreateDialog: " + account.getAccountNumber());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Resources resources = getResources();
        String message = resources.getString(R.string.create_account_fragment_message, resources.getString(account.getAccountType()));
        builder.setMessage(message)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        client.getAccounts().add(account);
                        Intent intent = new Intent(getActivity(), NavigationActivity.class);
                        intent.putExtra(CLIENT_KEY, client);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
