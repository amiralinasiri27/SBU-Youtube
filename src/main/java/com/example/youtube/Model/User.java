package com.example.youtube.Model;

import java.time.LocalDate;

public class User {
    /*  this is user class */

    private String username ;
    private String email ;
    private String ID;
    private String date;
    private String Age;
    private String password ;
    private String country;

    /** Constructors */
    public User(String username,String email,String date,String passWord,String country,String ID,String Age)
    {
     this.username=username;
     this.setDate(date);
     this.email=email;
     this.password=passWord;
     this.country=country;
     this.ID=ID;
     this.Age=Age;
    }

    public String getUsername() {
        return username;
    }

    public String getCountry() {
        return country;
    }

    public String getID() {
        return ID;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void changeCountry(String country) {
        this.country = country;
        //add database method
    }

    public void changePassword(String password) {
        this.password = password;
        //add database method
    }

    public void changeUsername(String username) {
        this.username = username;
        //add database method
        // you should know this is unique
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }
}
