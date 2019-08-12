package models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Account implements Transactable, Parcelable {

    private String accountNumber;
    private Double balance = new Double(0);
    private List<Transaction> transactions = new ArrayList<>();
    private int accountType;

    public Account() {
    }

    public Account(String accountNumber, Double balance, List<Transaction> transactions) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = transactions;
        this.accountType = 0;
    }

    @Override
    public void withdraw(Double amount, Client client) {

    }

    @Override
    public void deposit(Double amount, Client client) {

    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountNumber);
        dest.writeValue(this.balance);
        dest.writeList(this.transactions);
        dest.writeInt(this.accountType);
    }

    protected Account(Parcel in) {
        this.accountNumber = in.readString();
        this.balance = (Double) in.readValue(Double.class.getClassLoader());
        this.transactions = new ArrayList<>();
        in.readList(this.transactions, Transaction.class.getClassLoader());
        this.accountType = in.readInt();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}
