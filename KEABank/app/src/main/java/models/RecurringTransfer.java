package models;

import android.os.Parcel;
import android.os.Parcelable;

public class RecurringTransfer implements Parcelable {

    private Integer interval;
    private Account accountFrom;
    private Account accountTo;
    private Double amount;

    public RecurringTransfer(){}

    public RecurringTransfer(Integer interval, Account accountFrom, Account accountTo, Client client, Double amount){
        this.interval = interval;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.interval);
        dest.writeParcelable(this.accountFrom, flags);
        dest.writeParcelable(this.accountTo, flags);
        dest.writeValue(this.amount);
    }

    protected RecurringTransfer(Parcel in) {
        this.interval = (Integer) in.readValue(Integer.class.getClassLoader());
        this.accountFrom = in.readParcelable(Account.class.getClassLoader());
        this.accountTo = in.readParcelable(Account.class.getClassLoader());
        this.amount = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<RecurringTransfer> CREATOR = new Creator<RecurringTransfer>() {
        @Override
        public RecurringTransfer createFromParcel(Parcel source) {
            return new RecurringTransfer(source);
        }

        @Override
        public RecurringTransfer[] newArray(int size) {
            return new RecurringTransfer[size];
        }
    };
}
