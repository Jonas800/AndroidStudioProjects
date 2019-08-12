package com.example.keabank;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
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

import com.google.gson.Gson;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.Client;
import models.RecurringTransfer;
import models.Transaction;
import scheduler.PaymentService;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecurringTransfersFragment extends Fragment {
    final String CLIENT_KEY = "CLIENT_KEY";
    EditText amount, interval, transferTo;
    Account accountTo, accountFrom;
    Button submit;
    Spinner spinner;
    Client client;
    Context context;
    Resources resources;
    View rootView;

    public RecurringTransfersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_recurring_transfers, container, false);
        init();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItemId() != 0) {
                    if (!TextUtils.isEmpty(amount.getText()) || !TextUtils.isEmpty(transferTo.getText()) || !TextUtils.isEmpty(interval.getText())) {
                        Double amountDouble = Double.valueOf(amount.getText().toString());
                        Integer intervalInteger = Integer.valueOf(interval.getText().toString());
                        String accountToString = transferTo.getText().toString();
                        String accountFromString = spinner.getSelectedItem().toString();

                        Client otherClient = Client.getOtherClientDummyData();
                        boolean isCorrectOtherAccountNumber = false;
                        for (Account otherAccount : otherClient.getAccounts()) {
                            if (otherAccount.getAccountNumber().equals(accountToString)) {
                                isCorrectOtherAccountNumber = true;
                                accountTo = otherAccount;
                            }
                        }

                        for (Account account : client.getAccounts()) {
                            if (resources.getString(account.getAccountType()).equals(accountFromString)) {
                                accountFrom = account;
                            }
                        }
                        RecurringTransfer recurringTransfer = new RecurringTransfer(intervalInteger, accountFrom, accountTo, client, amountDouble);

                        client.getRecurringTransfers().add(recurringTransfer);

                        createRecurringPaymentsTable();

                        //TODO Create jobscheduler
                        JobScheduler jobScheduler = (JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                        ComponentName componentName = new ComponentName(context, PaymentService.class);

                        /*Gson gson = new Gson();
                        String jsonRecurringTransfer = gson.toJson(recurringTransfer);
                        String jsonClient = gson.toJson(client);*/

                        Parcel parcel = Parcel.obtain();
                        List<RecurringTransfer> temp = new ArrayList<>();
                        temp.add(recurringTransfer);
                        parcel.writeList(temp);
                        byte[] byt = parcel.marshall();
                        String dataRec = Base64.encodeToString(byt, 0, byt.length, 0);
                        parcel.recycle();

                        Parcel parcel1 = Parcel.obtain();
                        List<Client> temp1 = new ArrayList<>();
                        temp1.add(client);
                        parcel1.writeList(temp1);
                        byte[] byt1 = parcel1.marshall();
                        String dataClient = Base64.encodeToString(byt1, 0, byt1.length, 0);
                        parcel1.recycle();

                        Log.d("mybp", "onClick: " + recurringTransfer.getAccountFrom().toString());

                        PersistableBundle persistableBundle = new PersistableBundle();
                        persistableBundle.putString("job", dataRec);
                        persistableBundle.putString("client", dataClient);

                        JobInfo jobInfo = new JobInfo.Builder(1, componentName).setExtras(persistableBundle).setMinimumLatency(5000).build();

                        jobScheduler.schedule(jobInfo);

                        /*if (amountDouble <= account.getBalance()) {
                            try {
                                doTransaction(amountDouble);
                            } catch (IllegalArgumentException ex) {
                                makeErrorToast(R.string.transfer_pension_age_error);
                            }
                        } else {
                            makeOverdraftConfirmDialog(amountDouble);
                        }*/
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

    private void makeErrorToast(int errorMessage) {
        Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void createRecurringPaymentsTable() {
        TableLayout tableLayout = rootView.findViewById(R.id.recTable);
        tableLayout.setStretchAllColumns(true);

        while (tableLayout.getChildCount() > 1){
            tableLayout.removeView(tableLayout.getChildAt(tableLayout.getChildCount() - 1));
        }
        if (client.getRecurringTransfers() != null) {
            for (RecurringTransfer recurringTransfer : client.getRecurringTransfers()) {
                TableRow tableRow = new TableRow(context);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(lp);

                TextView accountFrom = new TextView(context);
                accountFrom.setText(recurringTransfer.getAccountFrom().getAccountNumber());

                TextView amount = new TextView(context);
                amount.setText(String.format("%.2f", recurringTransfer.getAmount()));

                TextView interval = new TextView(context);
                interval.setText(recurringTransfer.getInterval().toString() + " " + resources.getString(R.string.days));

                TextView accountTo = new TextView(context);
                accountTo.setText(recurringTransfer.getAccountTo().getAccountNumber());
                accountTo.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

                tableRow.addView(accountFrom);
                tableRow.addView(amount);
                tableRow.addView(interval);
                tableRow.addView(accountTo);
                tableLayout.addView(tableRow);
            }
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
        amount = rootView.findViewById(R.id.amount);
        transferTo = rootView.findViewById(R.id.account_to);
        spinner = rootView.findViewById(R.id.spinnerAccounts);
        interval = rootView.findViewById(R.id.interval);
        submit = rootView.findViewById(R.id.new_rec_payment);
        client = bundle.getParcelable(CLIENT_KEY);
        context = RecurringTransfersFragment.this.getContext();
        resources = getResources();
        createRecurringPaymentsTable();
        createAccountSpinner();
    }
}
