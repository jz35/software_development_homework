package com.example.Homework.POJO;

import java.math.BigDecimal;

/**
 * 食品实体类
 */
public class Food {
    private int foodId;      // fId
    private String foodName;  // fName
    private BigDecimal price; // fPrice
    private String type;      // fType (在售、停售、缺货)

    public Food() {
    }

    public Food(int foodId, String foodName, BigDecimal price, String type) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.type = type;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
} 