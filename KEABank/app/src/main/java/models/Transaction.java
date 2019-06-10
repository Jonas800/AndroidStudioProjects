package models;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Transaction implements Parcelable {

    private Double amount;
    private LocalDateTime timestamp;
    private String clientName;
    private Double balanceAtTransactionTime;

    public Transaction() {
    }

    public Transaction(Double amount, LocalDateTime timestamp, String clientName, Double balanceAtTransactionTime) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.clientName = clientName;
        this.balanceAtTransactionTime = balanceAtTransactionTime;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getBalanceAtTransactionTime() {
        return balanceAtTransactionTime;
    }

    public void setBalanceAtTransactionTime(Double balanceAtTransactionTime) {
        this.balanceAtTransactionTime = balanceAtTransactionTime;
    }

    public String toString(){
        String s = "";

        s += this.amount + ", ";
        s += this.clientName + ", ";
        s += this.balanceAtTransactionTime + ", ";
        s += this.timestamp.toString();

        return s;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.amount);
        dest.writeSerializable(this.timestamp);
        dest.writeString(this.clientName);
        dest.writeValue(this.balanceAtTransactionTime);
    }

    protected Transaction(Parcel in) {
        this.amount = (Double) in.readValue(Double.class.getClassLoader());
        this.timestamp = (LocalDateTime) in.readSerializable();
        this.clientName = in.readString();
        this.balanceAtTransactionTime = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
