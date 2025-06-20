package com.example.Homework.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Homework.POJO.Order;
import com.example.Homework.dao.OrderDAO;
import com.example.Homework.util.DBUtil;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, GROUP_CONCAT(f.fName SEPARATOR ', ') AS dishes " +
                    "FROM `order` o " +
                    "LEFT JOIN orderDetail od ON o.oId = od.oId " +
                    "LEFT JOIN food f ON od.fId = f.fId " +
                    "WHERE o.uId = ? " +
                    "GROUP BY o.oId " +
                    "ORDER BY o.oTime DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("oId"));
                order.setUserId(rs.getInt("uId"));
                order.setOrderTime(rs.getTimestamp("oTime"));
                order.setTotalPrice(rs.getBigDecimal("totalPrice"));
                order.setStatus(rs.getString("status"));
                order.setDishes(rs.getString("dishes"));
                
                orders.add(order);
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
        
        return orders;
    }

    @Override
    public Order getOrderById(int orderId) {
        Order order = null;
        String sql = "SELECT * FROM `order` WHERE oId = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("oId"));
                order.setUserId(rs.getInt("uId"));
                order.setOrderTime(rs.getTimestamp("oTime"));
                order.setTotalPrice(rs.getBigDecimal("totalPrice"));
                order.setStatus(rs.getString("status"));
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
        
        return order;
    }

    @Override
    public boolean deleteOrder(int orderId) {
        // 删除订单的操作涉及到两个表，需要使用事务
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        boolean success = false;
        
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // 开始事务
            
            // 先删除订单详情
            String sqlDetail = "DELETE FROM orderDetail WHERE oId = ?";
            pstmt1 = conn.prepareStatement(sqlDetail);
            pstmt1.setInt(1, orderId);
            pstmt1.executeUpdate();
            
            // 再删除订单
            String sqlOrder = "DELETE FROM `order` WHERE oId = ?";
            pstmt2 = conn.prepareStatement(sqlOrder);
            pstmt2.setInt(1, orderId);
            int rowsAffected = pstmt2.executeUpdate();
            
            conn.commit(); // 提交事务
            success = (rowsAffected > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            // 发生异常，回滚事务
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // 恢复自动提交
                }
                if (pstmt1 != null) pstmt1.close();
                if (pstmt2 != null) pstmt2.close();
                DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return success;
    }

    @Override
    public boolean cancelOrder(int orderId) {
        String sql = "UPDATE `order` SET status = '已取消' WHERE oId = ? AND status = '处理中'";
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            int rowsAffected = pstmt.executeUpdate();
            success = (rowsAffected > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return success;
    }
} 