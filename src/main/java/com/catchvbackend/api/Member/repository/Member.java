package com.catchvbackend.api.Member.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Member {

    private long id;

    private String userEmail;

    private String userPassword;

    private int loginStatus;

}
