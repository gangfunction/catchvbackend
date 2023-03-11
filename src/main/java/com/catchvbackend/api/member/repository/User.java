package com.catchvbackend.api.member.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class User {

    private long id;

    private String userEmail;

    private String userPassword;

    private int loginStatus;

}
