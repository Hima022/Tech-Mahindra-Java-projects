package com.tmf.servlets.dao;
//package com.tmf.servlets.dao;

import com.tmf.servlets.entity.User;

public interface UserDAO {

    int registerUser(User user) throws Exception;

    User loginUser(String email, String password) throws Exception;

    void updateProfile(int userId, String firstName, String lastName, String phone) throws Exception;

    User getUserById(int userId) throws Exception;
}
