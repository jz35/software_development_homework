package com.example.Homework.dao;

import com.example.Homework.POJO.User;

/**
 * 用户数据访问接口
 */
public interface UserDAO {
    /**
     * 根据用户名查找用户
     * 
     * @param username 用户名
     * @return 用户对象，如果未找到返回null
     */
    User findByUsername(String username);
    
    /**
     * 验证用户名和密码
     * 
     * @param username 用户名
     * @param password 密码
     * @return 验证成功返回true，否则返回false
     */
    boolean validateCredentials(String username, String password);
    
    /**
     * 验证用户的所有信息（用户名、密码、学院、专业）
     * 
     * @param username 用户名
     * @param password 密码
     * @param school 学院
     * @param department 专业
     * @return 验证成功返回true，否则返回false
     */
    boolean validateUserInfo(String username, String password, String school, String department);
} 