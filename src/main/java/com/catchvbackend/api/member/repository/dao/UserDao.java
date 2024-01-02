package com.catchvbackend.api.member.repository.dao;

import com.catchvbackend.api.member.repository.User;
import org.springframework.http.ResponseEntity;

public interface UserDao {

    void register(User user);

    int changeStatus(String userEmail);

    User findByEmail(String userEmail);

    ResponseEntity<?> login(String userEmail, String userPassword);
    void delete(String email);
    void edit(User user);
}
