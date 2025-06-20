package com.example.Homework.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Homework.POJO.Food;
import com.example.Homework.dao.FoodDAO;
import com.example.Homework.util.DBUtil;

public class FoodDAOImpl implements FoodDAO {

    @Override
    public List<Food> getAllAvailableFoods() {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT * FROM food WHERE fType = '在售' ORDER BY fId";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Food food = new Food();
                food.setFoodId(rs.getInt("fId"));
                food.setFoodName(rs.getString("fName"));
                food.setPrice(rs.getBigDecimal("fPrice"));
                food.setType(rs.getString("fType"));
                
                foods.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return foods;
    }

    @Override
    public Food getFoodById(int foodId) {
        Food food = null;
        String sql = "SELECT * FROM food WHERE fId = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, foodId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                food = new Food();
                food.setFoodId(rs.getInt("fId"));
                food.setFoodName(rs.getString("fName"));
                food.setPrice(rs.getBigDecimal("fPrice"));
                food.setType(rs.getString("fType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return food;
    }

    @Override
    public List<Food> getFoodsByOrderId(int orderId) {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT f.*, od.quantity " +
                    "FROM food f " +
                    "JOIN orderDetail od ON f.fId = od.fId " +
                    "WHERE od.oId = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Food food = new Food();
                food.setFoodId(rs.getInt("fId"));
                food.setFoodName(rs.getString("fName"));
                food.setPrice(rs.getBigDecimal("fPrice"));
                food.setType(rs.getString("fType"));
                
                foods.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return foods;
    }
} 