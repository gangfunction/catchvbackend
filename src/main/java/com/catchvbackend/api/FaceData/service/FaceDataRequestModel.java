package com.catchvbackend.api.FaceData.service;

import com.catchvbackend.api.FaceData.domain.face.FaceData;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record FaceDataRequestModel(List<FaceData> files, String userEmail, LocalDateTime startDate, String raw_len,
                                   String url) {
}