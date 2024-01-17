package com.catchvbackend.api.FaceData.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record FaceDataRequestModel(List<MultipartFile> files, String userEmail, String startDate, String raw_len,
                                   String url) {
}