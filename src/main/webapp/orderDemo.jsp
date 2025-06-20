<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的订单 - 演示</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 900px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .user-info {
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 1px solid #eee;
        }
        .user-info h2 {
            color: #333;
            font-size: 18px;
            margin: 0 0 10px 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
            color: #333;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .button-group {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }
        .button {
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border: none;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .delete-btn {
            background-color: #f44336;
            color: white;
        }
        .delete-btn:hover {
            background-color: #da190b;
        }
        .cancel-btn {
            background-color: #ff9800;
            color: white;
        }
        .cancel-btn:hover {
            background-color: #e68a00;
        }
        .button:disabled {
            background-color: #cccccc;
            cursor: not-allowed;
        }
        .no-orders {
            text-align: center;
            padding: 20px;
            color: #666;
            font-style: italic;
        }
        .price {
            font-weight: bold;
            color: #e53935;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .logout {
            color: #2196F3;
            text-decoration: none;
            padding: 5px 10px;
            border: 1px solid #2196F3;
            border-radius: 4px;
            font-size: 14px;
        }
        .logout:hover {
            background-color: #2196F3;
            color: white;
        }
    </style>
</head>
<body>
    <%
        // 检查用户是否已登录
        if (session.getAttribute("user") == null) {
            // 如果未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // 获取用户名
        com.example.Homework.POJO.User user = (com.example.Homework.POJO.User) session.getAttribute("user");
    %>
    <div class="container">
        <div class="header">
            <div class="user-info">
                <h2>用户名：<%= user.getUName() %></h2>
            </div>
            <a href="${pageContext.request.contextPath}/logout.jsp" class="logout">退出登录</a>
        </div>

        <form action="#" method="post" id="orderForm">
            <table>
                <thead>
                    <tr>
                        <th>选择</th>
                        <th>序号</th>
                        <th>订单ID</th>
                        <th>订单时间</th>
                        <th>订购菜肴</th>
                        <th>订单总价</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input type="checkbox" name="orderIds" value="ORD001"></td>
                        <td>1</td>
                        <td>ORD001</td>
                        <td>2023-11-05 12:30:45</td>
                        <td>红烧肉、清蒸鱼、炒青菜</td>
                        <td class="price">￥128.00</td>
                    </tr>
                    <tr>
                        <td><input type="checkbox" name="orderIds" value="ORD002"></td>
                        <td>2</td>
                        <td>ORD002</td>
                        <td>2023-11-10 18:15:22</td>
                        <td>宫保鸡丁、水煮肉片、蒜蓉西兰花</td>
                        <td class="price">￥156.50</td>
                    </tr>
                    <tr>
                        <td><input type="checkbox" name="orderIds" value="ORD003"></td>
                        <td>3</td>
                        <td>ORD003</td>
                        <td>2023-11-15 20:05:10</td>
                        <td>北京烤鸭、酸菜鱼、小炒肉</td>
                        <td class="price">￥235.00</td>
                    </tr>
                </tbody>
            </table>

            <div class="button-group">
                <button type="button" class="button delete-btn" id="deleteButton" disabled onclick="demoAction('delete')">
                    删除订单
                </button>
                <button type="button" class="button cancel-btn" id="cancelButton" disabled onclick="demoAction('cancel')">
                    撤销订单
                </button>
            </div>
        </form>
    </div>

    <script>
        // 监听复选框变化，更新按钮状态
        document.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
            checkbox.addEventListener('change', updateButtonState);
        });

        function updateButtonState() {
            const checkedBoxes = document.querySelectorAll('input[type="checkbox"]:checked');
            const hasSelection = checkedBoxes.length > 0;
            
            document.getElementById('deleteButton').disabled = !hasSelection;
            document.getElementById('cancelButton').disabled = !hasSelection;
        }

        function demoAction(action) {
            const checkedBoxes = document.querySelectorAll('input[type="checkbox"]:checked');
            const orderIds = Array.from(checkedBoxes).map(cb => cb.value).join(', ');
            
            if (action === 'delete') {
                alert(`演示：将删除订单 ${orderIds}`);
            } else if (action === 'cancel') {
                alert(`演示：将撤销订单 ${orderIds}`);
            }
        }
    </script>
</body>
</html> 