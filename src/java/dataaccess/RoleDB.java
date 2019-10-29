/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Role;

/**
 *
 * @author awarsyle
 */
public class RoleDB {
    public Role getRole(int roleID) throws SQLException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        
        Role    role;
        String  roleName;
        
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            String preparedQuery = "SELECT RoleID, RoleName FROM role_table WHERE RoleID=?";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ps.setInt(1, roleID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                roleName = rs.getString(2);
                role = new Role(roleID, roleName);
                return role;
            } else { // not found
                return null;
            }
        } finally {
            connectionPool.freeConnection(connection);
        }
    }
    
    public List<Role> getAll() throws SQLException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        
        int     roleID;
        String  roleName;
        Role    role;
        ArrayList<Role> roles = new ArrayList<>();
        
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            String preparedQuery = "SELECT RoleID, RoleName FROM role_table";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                roleID = rs.getInt(1);
                roleName = rs.getString(2);
                role = new Role(roleID, roleName);
                
                roles.add(role);
            }
            return roles;
        } finally {
            connectionPool.freeConnection(connection);
        }
    }
    
    public int insert(Role role) throws SQLException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            
            String preparedQuery =  "INSERT INTO role_table\n" +
                                    "(RoleID, RoleName)\n" +
                                    "VALUES (?, '?')";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ps.setInt(1, role.getRoleID());
            ps.setString(2, role.getRoleName());
            int rows = ps.executeUpdate();
            ps.close();
            return rows;
        } finally {
            connectionPool.freeConnection(connection);
        }
    }
    
    public int update(Role role) throws SQLException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            
            String preparedQuery = "UPDATE role_table SET RoleName = ? WHERE RoleID = ?";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ps.setString(1, role.getRoleName());
            int rows = ps.executeUpdate();
            ps.close();
            return rows;
        } finally {
            connectionPool.freeConnection(connection);
        }
    }
    
    public int delete(Role role) throws SQLException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            
            String preparedQuery = "DELETE FROM role_table where RoleID = ?";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ps.setInt(1, role.getRoleID());
            int rows = ps.executeUpdate();
            ps.close();
            return rows;
            
        } finally {
            connectionPool.freeConnection(connection);
        }
    }
}
