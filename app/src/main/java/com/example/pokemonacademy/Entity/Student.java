package com.example.pokemonacademy.Entity;


public class Student {

    int id;
    String name;
    String email;
    String password;
    String userType;
    int courseIndex;

    public Student(int id, String studentName, String email, String password, String userType, int courseIndex){
        name = studentName;
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.courseIndex = courseIndex;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getCourseIndex() {
        return courseIndex;
    }

    public String getName(){
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setCourseIndex(int courseIndex) {
        this.courseIndex = courseIndex;
    }
}
