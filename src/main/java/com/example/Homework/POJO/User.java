package com.example.Homework.POJO;

/**
 * 用户实体类
 */
public class User {
    private int uId;
    private String uName;
    private String uPw;
    private String uSchool;
    private String uDepartment;

    public User() {}

    public User(int uId, String uName, String uPw, String uSchool, String uDepartment) {
        this.uId = uId;
        this.uName = uName;
        this.uPw = uPw;
        this.uSchool = uSchool;
        this.uDepartment = uDepartment;
    }

    // Getters and Setters
    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUPw() {
        return uPw;
    }

    public void setUPw(String uPw) {
        this.uPw = uPw;
    }

    public String getUSchool() {
        return uSchool;
    }

    public void setUSchool(String uSchool) {
        this.uSchool = uSchool;
    }

    public String getUDepartment() {
        return uDepartment;
    }

    public void setUDepartment(String uDepartment) {
        this.uDepartment = uDepartment;
    }
} 