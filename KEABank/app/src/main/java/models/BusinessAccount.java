package models;

import android.os.Parcel;

import com.example.keabank.R;

import java.time.LocalDateTime;

public class BusinessAccount extends Account implements Transactable {

    public BusinessAccount(){
        setAccountType(R.string.menu_business_account);
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

    protected BusinessAccount(Parcel in) {
        super(in);
    }

    public static final Creator<BusinessAccount> CREATOR = new Creator<BusinessAccount>() {
        @Override
        public BusinessAccount createFromParcel(Parcel source) {
            return new BusinessAccount(source);
        }

        @Override
        public BusinessAccount[] newArray(int size) {
            return new BusinessAccount[size];
        }
    };
}
