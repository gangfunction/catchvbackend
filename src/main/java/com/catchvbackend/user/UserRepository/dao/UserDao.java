package com.catchvbackend.user.UserRepository.dao;

import com.catchvbackend.user.UserRepository.UserMember.User;

import java.util.Optional;

public interface UserDao {

    User register(User user);
    User login(Long id, String userPassword);
    Optional findById(Long id);
    Optional findByEmail(String userEmail);

    void delete(Long id);


}
