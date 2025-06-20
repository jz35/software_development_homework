package com.example.Homework.dao;

import java.util.List;

import com.example.Homework.POJO.Food;

/**
 * 食品数据访问接口
 */
public interface FoodDAO {
    /**
     * 获取所有在售食品
     * 
     * @return 食品列表
     */
    List<Food> getAllAvailableFoods();
    
    /**
     * 根据食品ID获取食品信息
     * 
     * @param foodId 食品ID
     * @return 食品对象，如果未找到返回null
     */
    Food getFoodById(int foodId);
    
    /**
     * 根据订单ID获取该订单中的所有食品
     * 
     * @param orderId 订单ID
     * @return 食品列表
     */
    List<Food> getFoodsByOrderId(int orderId);
} 