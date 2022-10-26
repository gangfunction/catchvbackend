package com.catchvbackend.api.member.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

    private long id;

    private String userEmail;

    private String userPassword;

    private int loginstatus;


}
