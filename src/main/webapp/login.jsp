<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录页面</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f5f5f5;
        }
        .login-container {
            background-color: white;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #666;
        }
        input[type="text"],
        input[type="password"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .error-message {
            color: #f44336;
            margin-bottom: 20px;
            text-align: center;
        }
        .submit-btn {
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .submit-btn:hover {
            background-color: #45a049;
        }
        .captcha-container {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .captcha-code {
            padding: 10px;
            background-color: #f0f0f0;
            border-radius: 4px;
            font-family: monospace;
            font-size: 18px;
            letter-spacing: 3px;
            color: #333;
            user-select: none;
        }
        .refresh-btn {
            padding: 8px 12px;
            background-color: #2196F3;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .refresh-btn:hover {
            background-color: #0b7dda;
        }
        .action-btn {
            padding: 10px 15px;
            background-color: #2196F3;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            text-align: center;
            width: 48%;
            box-sizing: border-box;
            display: inline-block;
        }
        .action-btn:hover {
            background-color: #0b7dda;
        }
    </style>

    <script>
        let captchaCode = '';

        function generateCaptcha() {
            // 只生成4位数字验证码
            captchaCode = '';
            for (let i = 0; i < 4; i++) {
                captchaCode += Math.floor(Math.random() * 10); // 0-9 的随机数字
            }
            document.getElementById("captcha").innerText = captchaCode;
            document.getElementById("hiddenCaptcha").value = captchaCode;
        }
        
        function updateDepartments() {
            const collegeSelect = document.getElementById("school");
            const departmentSelect = document.getElementById("department");
            const departments = {
                "计算机学院": ["软件工程", "计算机科学与技术", "网络工程"],
                "信息工程学院": ["信息安全", "通信工程", "电子工程"],
                "电子工程学院": ["通信工程", "电子信息工程", "自动化"]
            };

            const selectedCollege = collegeSelect.value;
            departmentSelect.innerHTML = '<option value="">请选择专业</option>';

            if (departments[selectedCollege]) {
                departments[selectedCollege].forEach(department => {
                    const option = document.createElement("option");
                    option.value = department;
                    option.text = department;
                    departmentSelect.add(option);
                });
            }
        }

        window.onload = generateCaptcha;
    </script>
</head>
<body>
    <div class="login-container">
        <h2>用户登录</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="username">用户名：</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">密码：</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="school">学院：</label>
                <select id="school" name="school" onchange="updateDepartments()" required>
                    <option value="">请选择学院</option>
                    <option value="计算机学院">计算机学院</option>
                    <option value="信息工程学院">信息工程学院</option>
                    <option value="电子工程学院">电子工程学院</option>
                </select>
            </div>
            <div class="form-group">
                <label for="department">专业：</label>
                <select id="department" name="department" required>
                    <option value="">请选择专业</option>
                </select>
            </div>
            <div class="form-group">
                <label for="userCaptcha">验证码：</label>
                <div class="captcha-container">
                    <input type="text" id="userCaptcha" name="userCaptcha" required>
                    <span id="captcha" class="captcha-code"></span>
                    <button type="button" class="refresh-btn" onclick="generateCaptcha()">刷新</button>
                </div>
                <input type="hidden" id="hiddenCaptcha" name="captcha">
            </div>
            <button type="submit" class="submit-btn">登录</button>
        </form>
        
        <div style="margin-top: 20px; display: flex; justify-content: space-between;">
            <a href="${pageContext.request.contextPath}/login" class="action-btn">注册</a>
            <a href="${pageContext.request.contextPath}/login" class="action-btn">忘记密码</a>
        </div>
        
<%--        <div style="margin-top: 20px; text-align: center; color: #666; font-size: 14px;">--%>
<%--            演示账号信息可以在这里显示--%>
<%--        </div>--%>
    </div>
</body>
</html> 