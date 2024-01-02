package com.catchvbackend.api.mainService.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FaceData {
    //이미지를 연속적으로 받는 서비스이므로 바이트를 리스트화했습니다.
    private int id;
    private byte[] image;
    private String name;
    private long size;
}
