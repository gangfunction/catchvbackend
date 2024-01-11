package com.catchvbackend.api.FaceData.repository;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class FaceResource{
    private String name;
    private byte[] image;
    private long size;
    
}
