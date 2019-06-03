package models;

import com.example.keabank.R;
import java.time.LocalDateTime;
import java.util.List;

public class BudgetAccount extends Account implements Transactable{
    //Is automatically given
    //Monthly deposits


    public BudgetAccount() {
    }

    public BudgetAccount(Double balance, List<Transaction> transactions) {
        super(balance, transactions);
        setAccountType(R.string.menu_budget_account);
    }

    @Override
    public void withdraw(Double amount, Client client) {
        Transaction transaction = new Transaction(0 - amount, LocalDateTime.now(), client, getBalance() - amount);
    }

    @Override
    public void deposit(Double amount, Client client) {
        Transaction transaction = new Transaction(amount, LocalDateTime.now(), client, getBalance() + amount);
    }
}
