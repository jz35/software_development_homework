package com.example.Homework.controller;

import java.io.IOException;

import com.example.Homework.POJO.User;
import com.example.Homework.dao.LoginDAO;
import com.example.Homework.dao.impl.LoginDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 注释掉或修改URL映射，以避免与LoginController冲突
// @WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private LoginDAO loginDAO;

    @Override
    public void init() throws ServletException {
        loginDAO = new LoginDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String school = request.getParameter("school");
        String department = request.getParameter("department");

        if (username == null || password == null || school == null || department == null ||
            username.trim().isEmpty() || password.trim().isEmpty() || 
            school.trim().isEmpty() || department.trim().isEmpty()) {
            request.setAttribute("error", "所有字段都必须填写");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        if (loginDAO.validateUserInfo(username, password, school, department)) {
            User user = loginDAO.findByUsername(username);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/order/list");
        } else {
            request.setAttribute("error", "用户名、密码、学院或专业信息不正确");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
} 