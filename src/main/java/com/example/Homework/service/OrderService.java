package com.example.Homework.service;

import java.util.List;

import com.example.Homework.POJO.Food;
import com.example.Homework.POJO.Order;
import com.example.Homework.dao.FoodDAO;
import com.example.Homework.dao.OrderDAO;
import com.example.Homework.dao.impl.FoodDAOImpl;
import com.example.Homework.dao.impl.OrderDAOImpl;

/**
 * 订单业务逻辑类
 */
public class OrderService {
    private OrderDAO orderDAO;
    private FoodDAO foodDAO;
    
    public OrderService() {
        this.orderDAO = new OrderDAOImpl();
        this.foodDAO = new FoodDAOImpl();
    }
    
    /**
     * 获取用户的所有订单
     * 
     * @param userId 用户ID
     * @return 订单列表
     */
    public List<Order> getUserOrders(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }
    
    /**
     * 删除订单
     * 
     * @param orderId 订单ID
     * @return 删除成功返回true，否则返回false
     */
    public boolean deleteOrder(int orderId) {
        // 检查订单是否存在
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            return false;
        }
        
        // 如果订单状态是"已完成"或"已取消"，才允许删除
        if ("已完成".equals(order.getStatus()) || "已取消".equals(order.getStatus())) {
            return orderDAO.deleteOrder(orderId);
        }
        
        return false;
    }
    
    /**
     * 取消订单
     * 
     * @param orderId 订单ID
     * @return 取消成功返回true，否则返回false
     */
    public boolean cancelOrder(int orderId) {
        // 检查订单是否存在
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            return false;
        }
        
        // 只有状态为"处理中"的订单才能被取消
        if ("处理中".equals(order.getStatus())) {
            return orderDAO.cancelOrder(orderId);
        }
        
        return false;
    }
    
    /**
     * 获取订单的所有菜品
     * 
     * @param orderId 订单ID
     * @return 菜品列表
     */
    public List<Food> getOrderFoods(int orderId) {
        return foodDAO.getFoodsByOrderId(orderId);
    }
} 