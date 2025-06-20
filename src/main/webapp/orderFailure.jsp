<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>订单操作失败</title>
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
            max-width: 500px;
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
        .success-part {
            color: #4CAF50;
            margin-bottom: 10px;
        }
        .failure-part {
            color: #f44336;
            margin-bottom: 10px;
        }
    </style>
    <script>
        // 页面加载完成后显示提示框
        window.onload = function() {
            // 检查是否有错误信息
            var errorMessage = '<%= session.getAttribute("errorMessage") %>';
            if (errorMessage && errorMessage !== 'null') {
                // 显示提示框
                alert(errorMessage);
                // 清除会话中的错误信息
                <% session.removeAttribute("errorMessage"); %>
            }
        };
    </script>
</head>
<body>
    <div class="container">
        <div class="icon">&#9888;</div>
        
        <div class="error">
            <%
            String errorType = request.getParameter("error");
            String action = request.getParameter("action");
            String errorMessage = (String) session.getAttribute("errorMessage");
            
            if (errorMessage != null && !errorMessage.isEmpty()) {
                // 如果有混合状态操作的错误信息，直接显示
                if (errorMessage.contains("成功")) {
                    // 分割成功和失败部分
                    String[] parts = errorMessage.split("；");
                    if (parts.length > 1) {
                        out.println("<div class='success-part'>" + parts[0] + "</div>");
                        out.println("<div class='failure-part'>" + parts[1] + "</div>");
                    } else {
                        out.println(errorMessage);
                    }
                } else {
                    out.println(errorMessage);
                }
            } else if (errorType != null) {
                if (errorType.equals("status")) {
                    if ("delete".equals(action)) {
                        out.println("删除失败：只有状态为'已完成'或'已取消'的订单可以删除。");
                    } else if ("cancel".equals(action)) {
                        out.println("取消失败：只有状态为'处理中'的订单可以取消。");
                    } else {
                        out.println("操作失败：订单状态不允许此操作。");
                    }
                } else if (errorType.equals("notfound")) {
                    out.println("操作失败：未找到指定的订单。");
                } else if (errorType.equals("database")) {
                    out.println("操作失败：数据库操作错误。");
                } else if (errorType.equals("noselection")) {
                    out.println("操作失败：请选择至少一个订单。");
                } else {
                    out.println("操作失败：" + errorType);
                }
            } else {
                out.println("操作失败：未知错误。");
            }
            %>
        </div>
        
        <a href="${pageContext.request.contextPath}/order/list" class="back-btn">返回订单列表</a>
    </div>
</body>
</html> 