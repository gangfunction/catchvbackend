package com.catchvbackend.usermanage.UserRepository.UserMember;

import lombok.Data;

@Data
public class User {

    private long id;

    private String userEmail;

    private String userPassword;

    private int loginstatus;

}
