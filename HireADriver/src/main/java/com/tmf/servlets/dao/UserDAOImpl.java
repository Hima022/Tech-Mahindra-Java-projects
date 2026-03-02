package com.tmf.servlets.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.servlets.util.DBConnection;
import com.tmf.servlets.entity.User;

public class UserDAOImpl implements UserDAO {

    
    public int registerUser(User user) throws Exception {

        int userId = 0;

        String sql = "INSERT INTO users(uname, first_name, last_name, email, phone_number, password, user_type, gender, dob) VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUname());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhoneNumber());
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getUserType());
            ps.setString(8, user.getGender());
            ps.setDate(9, user.getDob());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                userId = rs.getInt(1);
            }
        }

        return userId;
    }

    @Override
    public User loginUser(String email, String password) throws Exception {

        User user = null;

        String sql = "SELECT * FROM users WHERE email=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUname(rs.getString("uname"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setUserType(rs.getString("user_type"));
                user.setGender(rs.getString("gender"));
                user.setDob(rs.getDate("dob"));
            }
        }

        return user;
    }

    @Override
    public void updateProfile(int userId, String firstName, String lastName, String phone) throws Exception {

        String sql = "UPDATE users SET first_name=?, last_name=?, phone_number=? WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phone);
            ps.setInt(4, userId);

            ps.executeUpdate();
        }
    }

    @Override
    public User getUserById(int userId) throws Exception {

        User user = null;

        String sql = "SELECT * FROM users WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
            }
        }

        return user;
    }

	
}