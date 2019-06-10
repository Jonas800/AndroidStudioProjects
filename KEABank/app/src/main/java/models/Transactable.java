package models;

import myexceptions.AgeToLowException;

public interface Transactable {

    void withdraw(Double amount, Client client);
    void deposit(Double amount, Client client);
}
