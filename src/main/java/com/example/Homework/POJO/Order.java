package com.example.Homework.POJO;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

/**
 * 订单实体类
 */
public class Order {
    private int orderId;        // oId
    private int userId;         // uId
    private Date orderTime;     // oTime
    private BigDecimal totalPrice; // totalPrice
    private String status;      // status
    private List<OrderDetail> orderDetails; // 关联的订单详情
    private String dishes;      // 展示用的菜品名称列表

    public Order() {
    }

    public Order(int orderId, int userId, Date orderTime, BigDecimal totalPrice, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getDishes() {
        return dishes;
    }

    public void setDishes(String dishes) {
        this.dishes = dishes;
    }
} 