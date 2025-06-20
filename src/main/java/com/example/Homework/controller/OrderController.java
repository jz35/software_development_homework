package com.example.Homework.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.Homework.POJO.Order;
import com.example.Homework.POJO.User;
import com.example.Homework.service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "OrderController", urlPatterns = {"/order/*"})
public class OrderController extends HttpServlet {
    private OrderService orderService;
    private static final int RECORDS_PER_PAGE = 5;
    
    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        if (pathInfo == null || "/list".equals(pathInfo)) {
            // 获取当前页码，默认为第1页
            int currentPage = 1;
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                    if (currentPage < 1) {
                        currentPage = 1;
                    }
                } catch (NumberFormatException e) {
                    // 如果参数不是有效数字，使用默认值
                }
            }
            
            // 获取用户所有订单
            List<Order> allOrders = orderService.getUserOrders(user.getUId());
            
            // 计算总页数
            int totalOrders = allOrders.size();
            int totalPages = (int) Math.ceil((double) totalOrders / RECORDS_PER_PAGE);
            
            // 如果当前页超过总页数，设置为最后一页
            if (totalPages > 0 && currentPage > totalPages) {
                currentPage = totalPages;
            }
            
            // 计算当前页的起始索引和结束索引
            int startIndex = (currentPage - 1) * RECORDS_PER_PAGE;
            int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, totalOrders);
            
            // 获取当前页的订单
            List<Order> currentPageOrders = allOrders.subList(startIndex, endIndex);
            
            // 设置请求属性
            request.setAttribute("orders", currentPageOrders);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalOrders", totalOrders);
            
            // 转发到JSP页面
            request.getRequestDispatcher("/allOrder.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        if ("/manage".equals(pathInfo)) {
            String action = request.getParameter("action");
            String[] orderIds = request.getParameterValues("orderIds");
            
            if (orderIds == null || orderIds.length == 0) {
                // 没有选择订单
                response.sendRedirect(request.getContextPath() + 
                                     "/orderFailure.jsp?error=noselection&action=" + action);
                return;
            }
            
            // 成功处理的订单ID列表
            List<Integer> successOrderIds = new ArrayList<>();
            // 因状态不正确而失败的订单ID列表
            List<Integer> statusFailedOrderIds = new ArrayList<>();
            // 其他原因失败的订单ID列表
            List<Integer> otherFailedOrderIds = new ArrayList<>();
            
            for (String orderIdStr : orderIds) {
                int orderId = Integer.parseInt(orderIdStr);
                
                // 检查订单是否存在
                Order order = orderService.getUserOrders(user.getUId())
                                        .stream()
                                        .filter(o -> o.getOrderId() == orderId)
                                        .findFirst()
                                        .orElse(null);
                
                if (order == null) {
                    // 订单不存在或不属于当前用户
                    otherFailedOrderIds.add(orderId);
                    continue;
                }
                
                boolean success = false;
                
                if ("delete".equals(action)) {
                    // 删除订单
                    if (!"已完成".equals(order.getStatus()) && !"已取消".equals(order.getStatus())) {
                        // 状态不正确，不能删除
                        statusFailedOrderIds.add(orderId);
                        continue;
                    }
                    
                    success = orderService.deleteOrder(orderId);
                } else if ("cancel".equals(action)) {
                    // 取消订单
                    if (!"处理中".equals(order.getStatus())) {
                        // 状态不正确，不能取消
                        statusFailedOrderIds.add(orderId);
                        continue;
                    }
                    
                    success = orderService.cancelOrder(orderId);
                }
                
                if (success) {
                    successOrderIds.add(orderId);
                } else {
                    otherFailedOrderIds.add(orderId);
                }
            }
            
            // 获取当前页码，如果有的话
            String currentPage = request.getParameter("page");
            String redirectUrl = request.getContextPath() + "/order/list";
            if (currentPage != null && !currentPage.isEmpty()) {
                redirectUrl += "?page=" + currentPage;
            }
            
            // 处理结果
            if (statusFailedOrderIds.isEmpty() && otherFailedOrderIds.isEmpty()) {
                // 全部成功
                String successMessage = "";
                if ("delete".equals(action)) {
                    successMessage = "成功删除订单：" + formatOrderIds(successOrderIds);
                } else if ("cancel".equals(action)) {
                    successMessage = "成功撤销订单：" + formatOrderIds(successOrderIds);
                }
                
                request.getSession().setAttribute("successMessage", successMessage);
                response.sendRedirect(redirectUrl);
            } else if (!statusFailedOrderIds.isEmpty()) {
                // 有因状态不正确而失败的订单，跳转到失败页面
                StringBuilder errorMessage = new StringBuilder();
                
                if (!successOrderIds.isEmpty()) {
                    if ("delete".equals(action)) {
                        errorMessage.append("成功删除订单：").append(formatOrderIds(successOrderIds)).append("；");
                    } else if ("cancel".equals(action)) {
                        errorMessage.append("成功撤销订单：").append(formatOrderIds(successOrderIds)).append("；");
                    }
                }
                
                if ("delete".equals(action)) {
                    errorMessage.append("删除失败的订单：").append(formatOrderIds(statusFailedOrderIds));
                } else if ("cancel".equals(action)) {
                    errorMessage.append("撤销失败的订单：").append(formatOrderIds(statusFailedOrderIds));
                }
                
                request.getSession().setAttribute("errorMessage", errorMessage.toString());
                response.sendRedirect(request.getContextPath() + 
                                     "/orderFailure.jsp?error=status&action=" + action);
            } else {
                // 其他原因失败
                String errorType = otherFailedOrderIds.isEmpty() ? "unknown" : "database";
                response.sendRedirect(request.getContextPath() + 
                                     "/orderFailure.jsp?error=" + errorType + "&action=" + action);
            }
        }
    }
    
    /**
     * 格式化订单ID列表为字符串
     * @param orderIds 订单ID列表
     * @return 格式化后的字符串
     */
    private String formatOrderIds(List<Integer> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Integer orderId : orderIds) {
            sb.append(orderId).append(", ");
        }
        
        // 移除末尾的逗号和空格
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        
        return sb.toString();
    }
} 