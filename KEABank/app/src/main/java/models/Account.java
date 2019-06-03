package models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Account implements Parcelable, Transactable {

    private Double balance;
    private List<Transaction> transactions;
    private int accountType;

    public Account() {
    }

    public Account(Double balance, List<Transaction> transactions) {
        this.balance = balance;
        this.transactions = transactions;
        this.accountType = 0;
    }

    protected Account(Parcel parcel) {
        this.balance = parcel.readDouble();
        this.transactions = parcel.readArrayList(Transaction.class.getClassLoader());
        this.accountType = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.balance);
        dest.writeList(this.transactions);
        dest.writeInt(this.accountType);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Account createFromParcel(Parcel in) {
            return new Account();
        }

        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public static Creator<Account> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void withdraw(Double amount, Client client){

    }

    @Override
    public void deposit(Double amount, Client client){

    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
