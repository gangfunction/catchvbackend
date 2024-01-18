package com.catchvbackend.api.Member.repository;

import org.springframework.http.ResponseEntity;

public interface MemberTempRepository {

    void register(Member user);

    int changeStatus(String userEmail);

    Member findByEmail(String userEmail);

    ResponseEntity<?> login(String userEmail, String userPassword);
    void delete(String email);
    void edit(Member user);
}