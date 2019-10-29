package com.example.pokemonacademy.Entity;


import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable {

    private String id;
    private String name;
    private String userType;
    private int courseIndex;
    private int charId;
    private String firstTime;
    private String email;
    private String password;

    public User()    {

    }

    public User(String id, String studentName, String userType, int courseIndex, int charId, String firstTime){
        this.id = id;
        this.name = studentName;
        this.userType = userType;
        this.courseIndex = courseIndex;
        this.charId = charId;
        this.firstTime = firstTime;
    }

    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        userType = in.readString();
        courseIndex = in.readInt();
        charId = in.readInt();
        firstTime = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getCourseIndex() {
        return courseIndex;
    }


    public void setCourseIndex(int courseIndex) {
        this.courseIndex = courseIndex;
    }



    public int getCharId() {
        return charId;
    }

    public void setCharId(int charId) {
        this.charId = charId;
    }

    public String getFirstTime() { return firstTime; }

    public void setFirstTime(String firstTime)  { this.firstTime = firstTime; }

    public String getPassword() {return "pwd";}
    public void setPassword(String password) { this.password = "pwd";}
    public String getEmail() {return "email";}
    public void setEmail(String email) { this.email = email;}

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
