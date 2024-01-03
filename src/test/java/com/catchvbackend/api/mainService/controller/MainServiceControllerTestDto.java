package com.catchvbackend.api.mainService.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Builder
@RequiredArgsConstructor
public class MainServiceControllerTestDto implements Serializable {
    public List<MultipartFile> files;
    public String userEmail;
    public String startDate;
    public String raw_len;


}
