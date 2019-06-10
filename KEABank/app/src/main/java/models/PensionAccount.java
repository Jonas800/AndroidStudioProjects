package models;

import android.os.Parcel;

import com.example.keabank.R;

import java.time.LocalDateTime;

import myexceptions.AgeToLowException;

public class PensionAccount extends Account implements Transactable{
    //Can only withdraw after age 77

    public PensionAccount() {
        setAccountType(R.string.menu_pension_account);
    }

    @Override
    public void withdraw(Double amount, Client client) {
        /* Can only withdraw if the user is 77 or older */
        if (client.getAge() >= 77){
            Transaction transaction = new Transaction(0 - amount, LocalDateTime.now(), client.getName(), getBalance() - amount);
            getTransactions().add(0, transaction);
            setBalance(getBalance() + transaction.getAmount());
        } else{
            throw new IllegalArgumentException();
        }
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

    protected PensionAccount(Parcel in) {
        super(in);
    }

    public static final Creator<PensionAccount> CREATOR = new Creator<PensionAccount>() {
        @Override
        public PensionAccount createFromParcel(Parcel source) {
            return new PensionAccount(source);
        }

        @Override
        public PensionAccount[] newArray(int size) {
            return new PensionAccount[size];
        }
    };
}
