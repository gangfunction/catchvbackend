package com.catchvbackend.api.service.repository;

import lombok.Data;

@Data
public class ResultData {
    private int videoCount;
    private int detectCount;
    private String userEmail;
    private String urlList;
}
