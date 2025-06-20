<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录失败</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 100%;
            max-width: 400px;
        }
        .error {
            color: #f44336;
            font-size: 1.2em;
            margin-bottom: 30px;
        }
        .back-btn {
            display: inline-block;
            padding: 12px 24px;
            background-color: #2196F3;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        .back-btn:hover {
            background-color: #0b7dda;
        }
        .icon {
            font-size: 48px;
            color: #f44336;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="icon">&#9888;</div>
        
        <div class="error">
            <%
            String errorType = request.getParameter("error");
            if (errorType != null) {
                if (errorType.equals("credentials")) {
                    out.println("登录失败：用户名或密码错误。");
                } else if (errorType.equals("school")) {
                    out.println("登录失败：学院或系错误。");
                } else if (errorType.equals("captcha")) {
                    out.println("登录失败：验证码输入错误。");
                } else {
                    out.println("登录失败：请重新尝试。");
                }
            } else {
                out.println("登录失败：请重新尝试。");
            }
            %>
        </div>
        
        <a href="login.jsp" class="back-btn">返回登录页面</a>
    </div>
</body>
</html> 