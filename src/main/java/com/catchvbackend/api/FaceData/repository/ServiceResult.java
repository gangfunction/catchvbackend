package com.catchvbackend.api.FaceData.repository;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class ServiceResult{
    private Integer videoCount;
    private Integer detectCount;
    private String[] urlList;
    private String userEmail;

}
