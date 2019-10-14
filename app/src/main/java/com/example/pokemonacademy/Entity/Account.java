package com.example.pokemonacademy.Entity;

public class Account {
    private String username, email, password, course_index;
    public Account (String u, String e, String p, String c) {
        username = u;
        email = e;
        password = p;
        course_index = c;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getCourseIndex() {return course_index;}
}
