package com.example.kearateyourteacher.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

///////////////REMEMBER TO PUT FIELDS IN THE SAME ORDER CONSISTENTLY//////////////
public class CourseRating implements Parcelable {

    private String courseName;
    private Double finalRating;
    private String grade;

    public CourseRating() {
    }

    public CourseRating(String courseName, Double finalRating) {
        this.courseName = courseName;
        this.finalRating = finalRating;
    }

    protected CourseRating(Parcel parcel) {
        this.courseName = parcel.readString();
        this.finalRating = parcel.readDouble();
    }

    public void calculateFinalRating(ArrayList<Integer> ratings){
        double tempRating = 0;
        for (Integer rating : ratings) {
            tempRating += rating.intValue();
        }
        finalRating = new Double(tempRating / ratings.size());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.courseName);
        dest.writeDouble(this.finalRating);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CourseRating createFromParcel(Parcel in) {
            return new CourseRating(in);
        }

        public CourseRating[] newArray(int size) {
            return new CourseRating[size];
        }
    };

    public static Creator<CourseRating> getCREATOR() {
        return CREATOR;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getFinalRating() {
        return finalRating;
    }

    public void setFinalRating(Double finalRating) {
        this.finalRating = finalRating;
    }

    public String getGrade(){
        if(finalRating > 90){
            grade = "A";
        } else if(finalRating > 80){
            grade = "B";
        } else if(finalRating > 70){
            grade = "C";
        } else if(finalRating > 60){
            grade = "D";
        } else if(finalRating > 50){
            grade = "E";
        } else {
            grade = "Get a new job!";
        }

        return grade;
    }
}
