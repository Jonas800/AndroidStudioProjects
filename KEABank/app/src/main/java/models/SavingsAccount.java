package models;

import android.os.Parcel;

import com.example.keabank.R;

import java.time.LocalDateTime;

public class SavingsAccount extends Account implements Transactable{
    //Monthly deposits

    public SavingsAccount(){
        setAccountType(R.string.menu_savings_account);
    }

    @Override
    public void withdraw(Double amount, Client client) {
        Transaction transaction = new Transaction(0 - amount, LocalDateTime.now(), client.getName(), getBalance() - amount);
        getTransactions().add(0, transaction);
        setBalance(getBalance() + transaction.getAmount());    }

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

    protected SavingsAccount(Parcel in) {
        super(in);
    }

    public static final Creator<SavingsAccount> CREATOR = new Creator<SavingsAccount>() {
        @Override
        public SavingsAccount createFromParcel(Parcel source) {
            return new SavingsAccount(source);
        }

        @Override
        public SavingsAccount[] newArray(int size) {
            return new SavingsAccount[size];
        }
    };
}
