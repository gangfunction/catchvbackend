package com.catchvbackend.api.mainService.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResultData {
    //최소 바이트수의 용량을 위해서 int와 String을 활용했습니다.
    private int videoCount;
    private int detectCount;
    private String userEmail;
    private String urlList;

    public ResultData(int videoCount, int detectCount, String userEmail, String urlList) {
        this.videoCount = videoCount;
        this.detectCount = detectCount;
        this.userEmail = userEmail;
        this.urlList = urlList;
    }
}
