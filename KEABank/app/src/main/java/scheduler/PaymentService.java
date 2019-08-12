package scheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.Client;
import models.RecurringTransfer;

public class PaymentService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters){

        PersistableBundle bundle = jobParameters.getExtras();
        makePayment(bundle);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters){
        return false;
    }

    public void makePayment(PersistableBundle bundle){
        Gson gson = new Gson();

        String recurringTransferString = bundle.getString("job");
        RecurringTransfer recurringTransfer = decodeRecTransferFromString(recurringTransferString);
        Account accountFrom = recurringTransfer.getAccountFrom();
        Account accountTo = recurringTransfer.getAccountTo();
        Double amount = recurringTransfer.getAmount();

        String clientString = bundle.getString("client");
        Client client = decodeClientFromString(clientString);

        Log.d("mybp", "makePayment: " + accountFrom.toString());
        accountFrom.withdraw(amount, client);
        accountTo.deposit(amount, Client.getOtherClientDummyData());

        Log.d("mybp", "makePayment: Success");
    }

    private Client decodeClientFromString (String data) {
        byte[] byt = Base64.decode(data, 0);

        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(byt, 0, byt.length);
        parcel.setDataPosition(0);

        List<Client> temp = new ArrayList<>();
        parcel.readList(temp, getClass().getClassLoader());
        parcel.recycle();

        if (temp.size()>0) {
            return temp.get(0);
        } else {
            return null;
        }
    }

    private RecurringTransfer decodeRecTransferFromString (String data) {
        byte[] byt = Base64.decode(data, 0);

        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(byt, 0, byt.length);
        parcel.setDataPosition(0);

        List<RecurringTransfer> temp = new ArrayList<>();
        parcel.readList(temp, getClass().getClassLoader());
        parcel.recycle();

        if (temp.size()>0) {
            return temp.get(0);
        } else {
            return null;
        }
    }
}
