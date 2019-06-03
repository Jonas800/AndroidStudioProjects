package models;

import com.example.keabank.R;

import java.time.LocalDateTime;
import java.util.List;

public class DefaultAccount extends Account implements Transactable{
    //Is automatic
    //


    public DefaultAccount() {
    }

    public DefaultAccount(Double balance, List<Transaction> transactions) {
        super(balance, transactions);
        setAccountType(R.string.menu_default_account);
    }

    @Override
    public void withdraw(Double amount, Client client) {
        Transaction transaction = new Transaction(0 - amount, LocalDateTime.now(), client, getBalance() - amount);
        getTransactions().add(transaction);
        setBalance(getBalance() + transaction.getAmount());
    }

    @Override
    public void deposit(Double amount, Client client) {
        Transaction transaction = new Transaction(amount, LocalDateTime.now(), client, getBalance() + amount);

        getTransactions().add(transaction);

        setBalance(getBalance() + transaction.getAmount());
    }
}
