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

    public Client() {
    }

    public Client(String name, String email, String address, String city, List<Account> accounts) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.accounts = accounts;
    }

    protected Client(Parcel parcel) {
        this.name = parcel.readString();
        this.email = parcel.readString();
        this.address = parcel.readString();
        this.city = parcel.readString();
        ArrayList<Account> accountArrayList = new ArrayList<>();
        this.accounts = parcel.readArrayList(Account.class.getClassLoader());
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
        dest.writeList(this.accounts);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Client createFromParcel(Parcel in) {
            return new Client();
        }
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public static Creator<Client> getCREATOR(){
        return CREATOR;
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

    public static Client getDummyData() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Client client = new Client("Hans Hansen", firebaseUser.getEmail(), "Lygten 31", "København Nv", new ArrayList<Account>());

        List<Account> accounts = new ArrayList<>();
        Double amount1 = 12345.67;
        Double amount2 = 9999.12;

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(new Double(259), LocalDateTime.now().minusMonths(1), client, amount1 + 255));
        transactions.add(new Transaction(new Double(300), LocalDateTime.now().minusMonths(1), client, amount1 + 300));
        transactions.add(new Transaction(new Double(-500), LocalDateTime.now().minusMonths(1), client, amount1 + -500));


        DefaultAccount defaultAccount = new DefaultAccount(amount1, transactions);
        BudgetAccount budgetAccount = new BudgetAccount(amount2, transactions);
        accounts.add(defaultAccount);
        accounts.add(budgetAccount);

        client.setAccounts(accounts);

        return client;

    }
}
