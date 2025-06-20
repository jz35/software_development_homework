package com.example.Homework.dao;

import com.example.Homework.POJO.User;

public interface LoginDAO {
    User findByUsername(String username);
    boolean validateUser(String username, String password);
    boolean validateUserInfo(String username, String password, String school, String department);
} 