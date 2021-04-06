package com.example.homeautomation;

public class HomeAutomationDatabase {
    public String userEmail;
    public String userPass;
    public String userName;
    public HomeAutomationDatabase(String userName, String userEmail, String userPass) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
    }
}
