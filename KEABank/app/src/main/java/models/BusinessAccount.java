package models;

import java.time.LocalDateTime;

public class BusinessAccount  extends Account implements Transactable {

    @Override
    public void withdraw(Double amount, Client client) {
        Transaction transaction = new Transaction(0 - amount, LocalDateTime.now(), client, getBalance() - amount);
    }

    @Override
    public void deposit(Double amount, Client client) {

    }
}
