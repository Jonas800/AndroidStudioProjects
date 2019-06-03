package models;

public interface Transactable {

    void withdraw(Double amount, Client client);
    void deposit(Double amount, Client client);
}
