package com.example.Homework.POJO;

public class Login {
    private String username;
    private String password;
    private String college;
    private String department;

    public Login() {}

    public Login(String username, String password, String college, String department) {
        this.username = username;
        this.password = password;
        this.college = college;
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}