package com.example.Homework.dao;

import java.util.List;

import com.example.Homework.POJO.Order;

/**
 * 订单数据访问接口
 */
public interface OrderDAO {
    /**
     * 获取用户的所有订单
     * 
     * @param userId 用户ID
     * @return 订单列表
     */
    List<Order> getOrdersByUserId(int userId);
    
    /**
     * 根据订单ID获取订单
     * 
     * @param orderId 订单ID
     * @return 订单对象，如果未找到返回null
     */
    Order getOrderById(int orderId);
    
    /**
     * 删除订单
     * 
     * @param orderId 订单ID
     * @return 删除成功返回true，否则返回false
     */
    boolean deleteOrder(int orderId);
    
    /**
     * 更新订单状态为已取消
     * 
     * @param orderId 订单ID
     * @return 更新成功返回true，否则返回false
     */
    boolean cancelOrder(int orderId);
} 