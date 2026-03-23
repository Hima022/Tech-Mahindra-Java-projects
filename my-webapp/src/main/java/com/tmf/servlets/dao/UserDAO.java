package com.tmf.servlets.dao;


import com.tmf.servlets.entity.User;

public interface UserDAO {

	int registerUser(User user);

    User loginUser(String email, String password);

    User getUserById(int userId);


    void updateProfile(int userId,
                       String name,
                       String email,
                       String phone,
                       int age,
                       String password,
                       String image);

 
    void updateProfileWithoutImage(int userId,
                                  String name,
                                  String email,
                                  String phone,
                                  int age,
                                  String password);
}