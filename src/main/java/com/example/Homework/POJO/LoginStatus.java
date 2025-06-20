package com.example.Homework.POJO;

public class LoginStatus {
    private String courseName;
    private int score;

    public LoginStatus() {}

    public LoginStatus(String courseName, int score) {
        this.courseName = courseName;
        this.score = score;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
