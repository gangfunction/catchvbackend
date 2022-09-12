package com.catchvbackend.usermanage.UserRepository.dao;

import com.catchvbackend.usermanage.UserRepository.UserMember.User;

import java.util.Optional;

public interface UserDao {

    void register(User user);

    int changeStatus(String userEmail);

    Optional<User> findByEmail(String userEmail);

    void login(String userEmail, String userPassword);
    void delete(String email);
    void edit(User user);
}
