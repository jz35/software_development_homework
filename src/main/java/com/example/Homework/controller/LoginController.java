package com.example.Homework.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Homework.POJO.Login;
import com.example.Homework.POJO.User;
import com.example.Homework.service.LoginService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    // 学院和专业的映射关系
    private static final Map<String, List<String>> COLLEGE_DEPARTMENTS = new HashMap<>();
    
    static {
        COLLEGE_DEPARTMENTS.put("计算机学院", Arrays.asList("软件工程", "计算机科学与技术", "网络工程"));
        COLLEGE_DEPARTMENTS.put("信息工程学院", Arrays.asList("信息安全", "通信工程", "电子工程"));
        COLLEGE_DEPARTMENTS.put("电子工程学院", Arrays.asList("通信工程", "电子信息工程", "自动化"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 获取用户提交的数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String school = request.getParameter("school");
        String department = request.getParameter("department");
        String userCaptcha = request.getParameter("userCaptcha");
        String formCaptcha = request.getParameter("captcha");
        
        // 简单验证码验证 - 只检查用户输入的验证码与表单中的隐藏验证码是否匹配
        if (userCaptcha == null || formCaptcha == null || !userCaptcha.equals(formCaptcha)) {
            // 验证码错误，重定向到失败页面
            response.sendRedirect("loginFailure.jsp?error=captcha");
            return;
        }
        
        // 验证学院和专业的格式
        if (!isValidSchoolAndDepartment(school, department)) {
            // 学院或专业格式错误，重定向到失败页面
            response.sendRedirect("loginFailure.jsp?error=school");
            return;
        }
        
        // 封装到 Login 对象中
        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);
        login.setCollege(school);
        login.setDepartment(department);

        // 调用业务逻辑
        LoginService loginService = new LoginService();
        
        // 首先验证用户名和密码
        if (!loginService.validateCredentials(username, password)) {
            // 用户名或密码错误
            response.sendRedirect("loginFailure.jsp?error=credentials");
            return;
        }
        
        // 然后验证学院和专业是否匹配该用户
        if (!loginService.validateSchoolAndDepartment(username, school, department)) {
            // 学院或专业不匹配该用户
            response.sendRedirect("loginFailure.jsp?error=school");
            return;
        }
        
        // 获取用户完整信息
        User user = loginService.findUserByUsername(username);
        
        if (user != null) {
            // 登录成功，将用户信息存入 session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            // 重定向到订单管理页面，通过OrderController访问
            response.sendRedirect("order/list");
        } else {
            // 出现意外情况
            response.sendRedirect("loginFailure.jsp?error=unknown");
        }
    }
    
    /**
     * 验证学院和专业是否有效
     * 
     * @param school 学院名称
     * @param department 专业名称
     * @return 是否有效
     */
    private boolean isValidSchoolAndDepartment(String school, String department) {
        // 检查学院是否存在
        if (school == null || !COLLEGE_DEPARTMENTS.containsKey(school)) {
            return false;
        }
        
        // 检查专业是否属于该学院
        List<String> departments = COLLEGE_DEPARTMENTS.get(school);
        return department != null && departments.contains(department);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 对于 GET 请求，转发到登录页面
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
