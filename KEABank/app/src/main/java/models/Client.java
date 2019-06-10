package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Client implements Parcelable {

    private String name;
    private String email;
    private String address;
    private String city;
    private LocalDate dateOfBirth;
    private List<Account> accounts;
    private Affiliate affiliate;

    public Client() {
    }

    public Client(String name, String email, String address, String city, LocalDate dateOfBirth, List<Account> accounts, Affiliate affiliate) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.accounts = accounts;
        this.affiliate = affiliate;
    }

    public Affiliate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
    }

    public Integer getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public static Client getDummyData() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Client client = new Client("Hans Hansen", firebaseUser.getEmail(), "Lygten 31", "København Nv", LocalDate.of(1970, 7, 10), new ArrayList<Account>(), Affiliate.getCopenhagenAffiliate());

        List<Account> accounts = new ArrayList<>();
        Double amount1 = 12345.67;
        Double amount2 = 9999.12;
        String accountNumber1 = "0001";
        String accountNumber2 = "0002";

        List<Transaction> transactions = new ArrayList<>();
        List<Transaction> transactions2 = new ArrayList<>();
        transactions.add(new Transaction(new Double(259), LocalDateTime.now().minusMonths(1), client.getName(), amount1 + 255));
        transactions.add(new Transaction(new Double(300), LocalDateTime.now().minusMonths(1), client.getName(), amount1 + 300));
        transactions2.add(new Transaction(new Double(-500), LocalDateTime.now().minusMonths(1), client.getName(), amount1 + -500));


        DefaultAccount defaultAccount = new DefaultAccount(accountNumber1, amount1, transactions);
        BudgetAccount budgetAccount = new BudgetAccount(accountNumber2, amount2, transactions2);
        accounts.add(defaultAccount);
        accounts.add(budgetAccount);

        client.setAccounts(accounts);

        return client;

    }

    public static Client getOtherClientDummyData() {
        Client client = new Client("Bent Bentsen", "bent@test.dk", "Albani Torv 2", "Odense C", LocalDate.of(1920, 5, 12), new ArrayList<Account>(), Affiliate.getOdenseAffiliate());

        List<Account> accounts = new ArrayList<>();
        Double amount1 = 345.67;
        Double amount2 = 22222.12;
        String accountNumber1 = "0003";
        String accountNumber2 = "0004";

        List<Transaction> transactions1 = new ArrayList<>();
        List<Transaction> transactions2 = new ArrayList<>();
        transactions1.add(new Transaction(new Double(-500), LocalDateTime.now().minusMonths(1), client.getName(), amount1 + -500));

        DefaultAccount defaultAccount = new DefaultAccount(accountNumber1, amount1, transactions1);
        BudgetAccount budgetAccount = new BudgetAccount(accountNumber2, amount2, transactions2);
        accounts.add(defaultAccount);
        accounts.add(budgetAccount);

        client.setAccounts(accounts);

        return client;

    }

    @Override
    public String toString() {
        String s = "";

        s += getName() + ", ";
        s += getEmail() + ", ";
        s += getCity() + ", ";
        s += getAddress() + ", ";
        for (Account account : getAccounts()) {
            s += account.toString() + ": Balance: " + account.getBalance().toString() + ", AccountNumber: " + account.getAccountNumber() + ", " + "Transactions: " + account.getTransactions().toString() + ", ";
        }

        return s;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.address);
        dest.writeString(this.city);
        dest.writeSerializable(this.dateOfBirth);
        dest.writeList(this.accounts);
        dest.writeParcelable(this.affiliate, flags);
    }

    protected Client(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.address = in.readString();
        this.city = in.readString();
        this.dateOfBirth = (LocalDate) in.readSerializable();
        this.accounts = new ArrayList<>();
        in.readList(this.accounts, Account.class.getClassLoader());
        this.affiliate = in.readParcelable(Affiliate.class.getClassLoader());

    }


    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel source) {
            return new Client(source);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
}
