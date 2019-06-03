package models;

import java.time.LocalDateTime;

public class PensionAccount extends Account implements Transactable{
    //Can only withdraw after age 77

    public PensionAccount() {
    }

    @Override
    public void withdraw(Double amount, Client client) {
        /* Can only withdraw if the user is 77 or older */
        if (client.getAge() >= 77){
            Transaction transaction = new Transaction(0 - amount, LocalDateTime.now(), client, getBalance() - amount);

            //save transaction in db and adjust balance
        }
    }

    @Override
    public void deposit(Double amount, Client client) {
        Transaction transaction = new Transaction(amount, LocalDateTime.now(), client, getBalance() + amount);

        //save transaction
    }
}
