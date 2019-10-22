package com.example.pokemonacademy.Entity;


import android.os.Parcel;
import android.os.Parcelable;


public class Student implements Parcelable {

    private String id;
    private String name;
    private String userType;
    private int courseIndex;
    private int charId;
    private String firstTime;

    public Student()    {

    }

    public Student(String id, String studentName, String userType, int courseIndex, int charId, String firstTime){
        this.id = id;
        this.name = studentName;
        this.userType = userType;
        this.courseIndex = courseIndex;
        this.charId = charId;
        this.firstTime = firstTime;
    }

    protected Student(Parcel in) {
        id = in.readString();
        name = in.readString();
        userType = in.readString();
        courseIndex = in.readInt();
        charId = in.readInt();
        firstTime = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getId() {
        return id;
    }

    public int getCourseIndex() {
        return courseIndex;
    }

    public String getName(){
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setCourseIndex(int courseIndex) {
        this.courseIndex = courseIndex;
    }

    public String getUserType() {
        return userType;
    }

    public int getCharId() {
        return charId;
    }

    public void setCharId(int charId) {
        this.charId = charId;
    }

    public String getFirstTime() { return firstTime; }

    public void setFirstTime(String firstTime)  { this.firstTime = firstTime; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(userType);
        parcel.writeInt(courseIndex);
        parcel.writeInt(charId);
        parcel.writeString(firstTime);
    }
}
