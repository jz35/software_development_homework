package com.example.Homework.service;

import com.example.Homework.POJO.Login;
import com.example.Homework.POJO.User;
import com.example.Homework.dao.UserDAO;
import com.example.Homework.dao.impl.UserDAOImpl;

public class LoginService {
    private UserDAO userDAO;

    public LoginService() {
        this.userDAO = new UserDAOImpl();
    }

    /**
     * 验证用户登录
     * @param login 登录信息对象
     * @return 成功返回User对象，失败返回null
     */
    public User validateLogin(Login login) {
        // 首先验证用户名和密码
        if (login == null || login.getUsername() == null || login.getPassword() == null) {
            return null;
        }

        // 验证用户名和密码是否匹配
        boolean credentialsValid = userDAO.validateCredentials(login.getUsername(), login.getPassword());
        if (!credentialsValid) {
            return null;
        }

        // 如果需要验证学院和专业信息
        if (login.getCollege() != null && login.getDepartment() != null) {
            boolean allInfoValid = userDAO.validateUserInfo(
                login.getUsername(), 
                login.getPassword(),
                login.getCollege(),
                login.getDepartment()
            );
            
            if (!allInfoValid) {
                return null;
            }
        }

        // 获取用户完整信息
        return userDAO.findByUsername(login.getUsername());
    }
    
    /**
     * 验证用户名和密码
     * @param username 用户名
     * @param password 密码
     * @return 是否有效
     */
    public boolean validateCredentials(String username, String password) {
        return userDAO.validateCredentials(username, password);
    }
    
    /**
     * 验证学院和专业是否匹配该用户
     * @param username 用户名
     * @param school 学院
     * @param department 专业
     * @return 是否匹配
     */
    public boolean validateSchoolAndDepartment(String username, String school, String department) {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return false;
        }
        
        return school.equals(user.getUSchool()) && department.equals(user.getUDepartment());
    }
    
    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户对象，如果不存在则返回null
     */
    public User findUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}

