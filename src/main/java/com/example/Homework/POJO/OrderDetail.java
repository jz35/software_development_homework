package com.example.Homework.POJO;

import java.math.BigDecimal;

/**
 * 订单详情实体类
 */
public class OrderDetail {
    private int orderId;      // oId
    private int foodId;       // fId
    private int quantity;     // quantity
    private BigDecimal sum;   // sum
    private Food food;        // 关联的食物信息

    public OrderDetail() {
    }

    public OrderDetail(int orderId, int foodId, int quantity, BigDecimal sum) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.sum = sum;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
} 