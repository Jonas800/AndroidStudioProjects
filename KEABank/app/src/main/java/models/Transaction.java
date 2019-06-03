package models;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Transaction implements Parcelable {

    private Double amount;
    private LocalDateTime timestamp;
    private Client client;
    private Double balanceAtTransactionTime;

    public Transaction() {
    }

    public Transaction(Double amount, LocalDateTime timestamp, Client client, Double balanceAtTransactionTime) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.client = client;
        this.balanceAtTransactionTime = balanceAtTransactionTime;
    }

    protected Transaction(Parcel parcel) {
        this.amount = parcel.readDouble();
        this.timestamp = (LocalDateTime) parcel.readValue(LocalDateTime.class.getClassLoader());
        this.client = (Client) parcel.readValue(Client.class.getClassLoader());
        this.balanceAtTransactionTime = parcel.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.amount);
        dest.writeValue(this.timestamp);
        dest.writeValue(this.client);
        dest.writeDouble(this.balanceAtTransactionTime);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Transaction createFromParcel(Parcel in) {
            return new Transaction();
        }

        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public static Creator<Transaction> getCREATOR(){
        return CREATOR;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getBalanceAtTransactionTime() {
        return balanceAtTransactionTime;
    }

    public void setBalanceAtTransactionTime(Double balanceAtTransactionTime) {
        this.balanceAtTransactionTime = balanceAtTransactionTime;
    }
}
