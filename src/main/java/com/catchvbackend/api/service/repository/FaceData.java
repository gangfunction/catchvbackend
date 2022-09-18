package com.catchvbackend.api.service.repository;

import lombok.Data;

@Data
public class FaceData {
    private int id;
    private byte[] image;
    private String name;
    private long size;
}
