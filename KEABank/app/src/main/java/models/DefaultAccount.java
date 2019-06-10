package models;

import android.os.Parcel;

import com.example.keabank.R;

import java.time.LocalDateTime;
import java.util.List;

public class DefaultAccount extends Account implements Transactable{
    //Is automatic
    //


    public DefaultAccount() {
        setAccountType(R.string.menu_default_account);
    }

    public DefaultAccount(String accountNumber, Double balance, List<Transaction> transactions) {
        super(accountNumber, balance, transactions);
        setAccountType(R.string.menu_default_account);
    }

    @Override
    public void withdraw(Double amount, Client client) {
        Transaction transaction = new Transaction(0 - amount, LocalDateTime.now(), client.getName(), getBalance() - amount);
        getTransactions().add(0, transaction);
        setBalance(getBalance() + transaction.getAmount());
    }

    @Override
    public void deposit(Double amount, Client client) {
        Transaction transaction = new Transaction(amount, LocalDateTime.now(), client.getName(), getBalance() + amount);
        getTransactions().add(0, transaction);
        setBalance(getBalance() + transaction.getAmount());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected DefaultAccount(Parcel in) {
        super(in);
    }

    public static final Creator<DefaultAccount> CREATOR = new Creator<DefaultAccount>() {
        @Override
        public DefaultAccount createFromParcel(Parcel source) {
            return new DefaultAccount(source);
        }

        @Override
        public DefaultAccount[] newArray(int size) {
            return new DefaultAccount[size];
        }
    };
}
