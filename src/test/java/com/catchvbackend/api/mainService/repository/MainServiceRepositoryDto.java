package com.catchvbackend.api.mainService.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Getter
@RequiredArgsConstructor
public class MainServiceRepositoryDto {
    private byte[] image;
    private String name;
    private long size;
    private int videoCount;
    private int detectCount;
    private String userEmail;
    private String urlList;

}
