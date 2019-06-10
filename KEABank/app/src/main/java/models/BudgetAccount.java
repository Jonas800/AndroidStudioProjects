package models;

import android.os.Parcel;

import com.example.keabank.R;
import java.time.LocalDateTime;
import java.util.List;

public class BudgetAccount extends Account implements Transactable{
    //Is automatically given
    //Monthly deposits


    public BudgetAccount() {
    }

    public BudgetAccount(String accountNumber, Double balance, List<Transaction> transactions) {
        super(accountNumber, balance, transactions);
        setAccountType(R.string.menu_budget_account);
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

    protected BudgetAccount(Parcel in) {
        super(in);
    }

    public static final Creator<BudgetAccount> CREATOR = new Creator<BudgetAccount>() {
        @Override
        public BudgetAccount createFromParcel(Parcel source) {
            return new BudgetAccount(source);
        }

        @Override
        public BudgetAccount[] newArray(int size) {
            return new BudgetAccount[size];
        }
    };
}
