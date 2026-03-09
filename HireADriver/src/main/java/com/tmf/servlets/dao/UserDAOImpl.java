package com.tmf.servlets.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.servlets.util.DBConnection;
import com.tmf.servlets.entity.User;

public class UserDAOImpl implements UserDAO {

    Connection con = DBConnection.getConnection();

    @Override
    public int registerUser(User user) {

        int userId = 0;

        try {

            String sql =
            "INSERT INTO users(name,email,password,phone,user_type) VALUES(?,?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getUserType());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                userId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userId;
    }

    @Override
    public User loginUser(String email, String password) {

        User user = null;

        try {

            String sql =
            "SELECT * FROM users WHERE email=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setUserType(rs.getString("user_type"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User getUserById(int userId) {

        User user = null;

        try {

            String sql =
            "SELECT * FROM users WHERE user_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setUserType(rs.getString("user_type"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
		

	@Override
	public void updateProfile(int userId, String name, String email, String phone, int age, String ProfileImage) {
		// TODO Auto-generated method stub
		try {

			String sql="UPDATE users SET name=?,email=?,phone=?,age=?,profile_image=? WHERE user_id=?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, phone);
			ps.setInt(4, age);
			ps.setInt(5, userId);
			ps.setString(6, ProfileImage);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}

	@Override
	public void updateProfileWithoutImage(int userId, String name, String email, String phone, int age) {
		// TODO Auto-generated method stub
		
	}


}
