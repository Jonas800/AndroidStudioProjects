package com.example.keabank;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import helpers.MinMaxInputFilter;
import models.Account;
import models.Client;
import models.Transaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    final String CLIENT_KEY = "CLIENT_KEY", ACCOUNT_KEY = "ACCOUNT_KEY";
    EditText amount;
    Button transfer;
    Spinner spinner;
    Account account;
    Client client;
    Context context;
    Resources resources;
    View rootView;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        init();



        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItemId() != 0) {
                    if (!TextUtils.isEmpty(amount.getText())) {
                        Double amountDouble = Double.valueOf(amount.getText().toString());

                        if (amountDouble <= account.getBalance()) {
                            try {
                                doTransaction(amountDouble);
                            } catch (IllegalArgumentException ex) {
                                makeErrorToast(R.string.transfer_pension_age_error);
                            }
                        } else {
                            makeOverdraftConfirmDialog(amountDouble);
                        }
                    } else {
                        makeErrorToast(R.string.transfer_text_error);
                    }
                } else {
                    makeErrorToast(R.string.transfer_spinner_error);
                }
            }
        });

        return rootView;
    }

    private void doTransaction(Double amountDouble) {
        String selectedAccount = spinner.getSelectedItem().toString();
        for (Account accountToTransferTo : client.getAccounts()) {
            //make sure accounts aren't the same account
            if (resources.getString(accountToTransferTo.getAccountType()).equals(selectedAccount)) {
                if (accountToTransferTo != account) {
                    //create a nemid dialog for pension accounts
                    if (resources.getString(R.string.menu_pension_account).equals(selectedAccount)) {
                        makeNemIdDialog(accountToTransferTo, amountDouble);
                    } else {
                        try {
                            account.withdraw(amountDouble, client);
                            accountToTransferTo.deposit(amountDouble, client);
                            //remake transaction table with new transaction
                            createTransactionTable();
                            break;
                        } catch (IllegalArgumentException ex) {
                            makeErrorToast(R.string.transfer_pension_age_error);
                        }
                    }
                } else {
                    makeErrorToast(R.string.transfer_same_account_error);
                }
            }
        }
    }

    private void makeNemIdDialog(final Account accountToTransferTo, final Double amountDouble) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_nem_id);
        dialog.setTitle(getResources().getString(R.string.nemid));

        final EditText userID = dialog.findViewById(R.id.nemid_userid);
        final EditText password = dialog.findViewById(R.id.nemid_password);

        Button dialogButton = (Button) dialog.findViewById(R.id.nemid_button);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID.getText().toString().equals("0101901212") && password.getText().toString().equals("123456")) {
                    final Dialog keyDialog = new Dialog(context);
                    keyDialog.setContentView(R.layout.dialog_nem_id_key);
                    dialog.setTitle(resources.getString(R.string.nemid));

                    final EditText key = keyDialog.findViewById(R.id.nemid_key);
                    TextView textKey = keyDialog.findViewById(R.id.nemid_text_key);
                    textKey.setText("" + new Random().nextInt(9999));
                    Button keyDialogButton = keyDialog.findViewById(R.id.nemid_button);

                    keyDialogButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (key.getText().toString().equals("010101")) {
                                account.withdraw(amountDouble, client);
                                accountToTransferTo.deposit(amountDouble, client);
                                //remake transaction table with new transaction
                                createTransactionTable();
                                keyDialog.dismiss();
                            } else {
                                makeErrorToast(R.string.nemid_key_error);
                            }
                        }
                    });
                    dialog.dismiss();
                    keyDialog.show();
                } else {
                    //make cancel button too
                    makeErrorToast(R.string.nemid_error);
                }


            }
        });

        dialog.show();
    }

    private void makeOverdraftConfirmDialog(final Double amountDouble) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.overdraft_confirm_dialog_message);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTransaction(amountDouble);
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

    private void createTransactionTable() {
        TableLayout tableLayout = rootView.findViewById(R.id.transactionHolder);
        tableLayout.removeAllViews();
        tableLayout.setStretchAllColumns(true);

        TextView balance = rootView.findViewById(R.id.balance_amount);
        balance.setText(String.format("%.2f", account.getBalance()));

        for (Transaction transaction : account.getTransactions()) {
            TableRow tableRow = new TableRow(context);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(lp);

            String message = resources.getString(R.string.create_account_fragment_message, resources.getString(account.getAccountType()));

            TextView labelTimestamp = new TextView(context);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("kk:mm - dd/MM/y");
            labelTimestamp.setText(transaction.getTimestamp().format(dateTimeFormatter));

            TextView labelClient = new TextView(context);
            labelClient.setText(transaction.getClientName());

            TextView labelAmount = new TextView(context);
            labelAmount.setText(String.format("%.2f", transaction.getAmount()));
            labelAmount.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

            tableRow.addView(labelTimestamp);
            tableRow.addView(labelClient);
            tableRow.addView(labelAmount);
            tableLayout.addView(tableRow);
        }
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
        Bundle bundle = getArguments();
        client = bundle.getParcelable(CLIENT_KEY);
        account = bundle.getParcelable(ACCOUNT_KEY);
        amount = rootView.findViewById(R.id.amount);
        //amount.setFilters(new InputFilter[]{new MinMaxInputFilter(1, account.getBalance())});
        transfer = rootView.findViewById(R.id.transfer);
        spinner = rootView.findViewById(R.id.spinnerAccounts);
        context = AccountFragment.this.getContext();
        resources = getResources();
        createTransactionTable();
        createAccountSpinner();
    }
}
