package models;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class Affiliate implements Parcelable {

    private String name;
    private String address;
    private String city;

    private Affiliate(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }

    public static Affiliate getCopenhagenAffiliate(){
        return new Affiliate("KEA Bank København", "Lygten 37", "2400 København");
    }
    public static Affiliate getOdenseAffiliate(){
        return new Affiliate("KEA Bank Odense", "Albani Torv 1", "5100 Odense");
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public static Affiliate getClosestAffiliate(Address clientAddress, Context context) {
        try {
            Location locationClient = new Location("client");
            locationClient.setLatitude(clientAddress.getLatitude());
            locationClient.setLongitude(clientAddress.getLongitude());

            Geocoder geocoder = new Geocoder(context);
            Location locationCopenhagen = new Location("Copenhagen");
            List<Address> addresses = geocoder.getFromLocationName("Lygten 37, 2400 København", 1);
            locationCopenhagen.setLongitude(addresses.get(0).getLongitude());
            locationCopenhagen.setLatitude(addresses.get(0).getLatitude());

            Location locationOdense = new Location("Odense");
            addresses = geocoder.getFromLocationName("Albana Torv 1, Odense C", 1);
            locationCopenhagen.setLongitude(addresses.get(0).getLongitude());
            locationCopenhagen.setLatitude(addresses.get(0).getLatitude());

            float distanceToCopenhagen = locationClient.distanceTo(locationCopenhagen);
            float distanceToOdense = locationClient.distanceTo(locationOdense);

            if(distanceToCopenhagen > distanceToOdense){
                return getOdenseAffiliate();
            } else{
                return getCopenhagenAffiliate();
            }
        } catch (IOException ex) {
            return null;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.city);
    }

    protected Affiliate(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.city = in.readString();
    }

    public static final Parcelable.Creator<Affiliate> CREATOR = new Parcelable.Creator<Affiliate>() {
        @Override
        public Affiliate createFromParcel(Parcel source) {
            return new Affiliate(source);
        }

        @Override
        public Affiliate[] newArray(int size) {
            return new Affiliate[size];
        }
    };
}
