package com.catchvbackend.api.mainService.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FaceData {
    private int id;
    private byte[] image;
    private String name;
    private long size;
}
