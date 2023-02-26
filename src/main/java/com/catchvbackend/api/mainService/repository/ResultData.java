package com.catchvbackend.api.mainService.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ResultData {
    private int videoCount;
    private int detectCount;
    private String userEmail;
    private String urlList;
}
