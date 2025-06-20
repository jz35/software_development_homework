<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的订单</title>
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
        .error-message {
            color: #f44336;
            margin-bottom: 20px;
            text-align: center;
            font-weight: bold;
        }
        .success-message {
            color: #4CAF50;
            margin-bottom: 20px;
            text-align: center;
            font-weight: bold;
        }
        .status-completed {
            color: #4CAF50;
            font-weight: bold;
        }
        .status-processing {
            color: #2196F3;
            font-weight: bold;
        }
        .status-cancelled {
            color: #9e9e9e;
            font-weight: bold;
        }
        /* 分页样式 */
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
            gap: 5px;
        }
        .pagination a, .pagination span {
            padding: 8px 12px;
            text-decoration: none;
            border: 1px solid #ddd;
            color: #666;
            border-radius: 4px;
        }
        .pagination a:hover {
            background-color: #f5f5f5;
        }
        .pagination .active {
            background-color: #2196F3;
            color: white;
            border-color: #2196F3;
        }
        .pagination .disabled {
            color: #ccc;
            cursor: not-allowed;
        }
        .pagination-info {
            text-align: center;
            margin-top: 10px;
            color: #666;
            font-size: 14px;
        }
    </style>
    <script>
        // 页面加载完成后显示成功消息
        window.onload = function() {
            // 检查是否有成功消息
            var successMessage = '<%= session.getAttribute("successMessage") %>';
            if (successMessage && successMessage !== 'null') {
                // 显示提示框
                alert(successMessage);
            }
            
            // 初始化验证码（如果有的话）
            if (typeof generateCaptcha === 'function') {
                generateCaptcha();
            }
        };
        
        // 监听复选框变化，更新按钮状态
        document.addEventListener('DOMContentLoaded', function() {
            document.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
                checkbox.addEventListener('change', updateButtonState);
            });
        });

        function updateButtonState() {
            const checkedBoxes = document.querySelectorAll('input[type="checkbox"]:checked');
            const hasSelection = checkedBoxes.length > 0;
            
            document.getElementById('deleteButton').disabled = !hasSelection;
            document.getElementById('cancelButton').disabled = !hasSelection;
        }

        function submitForm(action) {
            document.getElementById('actionType').value = action;
            document.getElementById('orderForm').submit();
        }
    </script>
</head>
<body>
    <%
    // 检查用户是否已登录
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    
    com.example.Homework.POJO.User user = (com.example.Homework.POJO.User) session.getAttribute("user");
    
    // 获取成功消息并清除
    String successMessage = (String) session.getAttribute("successMessage");
    if (successMessage != null) {
        session.removeAttribute("successMessage");
    }
    %>

    <div class="container">
        <div class="header">
            <div class="user-info">
                <h2>用户名：<%= user.getUName() %></h2>
            </div>
            <a href="${pageContext.request.contextPath}/logout.jsp" class="logout">退出登录</a>
        </div>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <% if (successMessage != null) { %>
            <div class="success-message">
                <%= successMessage %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/order/manage" method="post" id="orderForm">
            <table>
                <thead>
                    <tr>
                        <th>选择</th>
                        <th>序号</th>
                        <th>订单ID</th>
                        <th>订单时间</th>
                        <th>订购菜肴</th>
                        <th>订单总价</th>
                        <th>状态</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty orders}">
                            <c:forEach items="${orders}" var="order" varStatus="status">
                                <tr>
                                    <td><input type="checkbox" name="orderIds" value="${order.orderId}"></td>
                                    <td>${(currentPage-1)*5 + status.index + 1}</td>
                                    <td>${order.orderId}</td>
                                    <td><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>${order.dishes}</td>
                                    <td class="price">￥<fmt:formatNumber value="${order.totalPrice}" type="number" pattern="#,##0.00"/></td>
                                    <td class="
                                        <c:choose>
                                            <c:when test="${order.status eq '已完成'}">status-completed</c:when>
                                            <c:when test="${order.status eq '处理中'}">status-processing</c:when>
                                            <c:when test="${order.status eq '已取消'}">status-cancelled</c:when>
                                        </c:choose>
                                    ">${order.status}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="7" class="no-orders">暂无订单信息</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>

            <!-- 分页控件 -->
            <c:if test="${totalPages > 0}">
                <div class="pagination-info">
                    共 ${totalOrders} 条记录，当前显示第 ${currentPage} 页，共 ${totalPages} 页
                </div>
                <div class="pagination">
                    <!-- 上一页按钮 -->
                    <c:choose>
                        <c:when test="${currentPage > 1}">
                            <a href="${pageContext.request.contextPath}/order/list?page=${currentPage - 1}">上一页</a>
                        </c:when>
                        <c:otherwise>
                            <span class="disabled">上一页</span>
                        </c:otherwise>
                    </c:choose>
                    
                    <!-- 页码按钮 -->
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage == i}">
                                <span class="active">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/order/list?page=${i}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    
                    <!-- 下一页按钮 -->
                    <c:choose>
                        <c:when test="${currentPage < totalPages}">
                            <a href="${pageContext.request.contextPath}/order/list?page=${currentPage + 1}">下一页</a>
                        </c:when>
                        <c:otherwise>
                            <span class="disabled">下一页</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <div class="button-group">
                <button type="button" class="button delete-btn" id="deleteButton" disabled onclick="submitForm('delete')">
                    删除订单
                </button>
                <button type="button" class="button cancel-btn" id="cancelButton" disabled onclick="submitForm('cancel')">
                    撤销订单
                </button>
                <input type="hidden" name="action" id="actionType" value="">
                <input type="hidden" name="page" value="${currentPage}">
            </div>
        </form>
    </div>
</body>
</html> 