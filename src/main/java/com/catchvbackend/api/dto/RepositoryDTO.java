package com.catchvbackend.api.dto;

import com.catchvbackend.api.common.QueueStatus;
import com.catchvbackend.domain.face.FaceData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class RepositoryDTO {
    private Long id;
    private String userEmail;
    private String rawLen;
    private Integer detectCount;
    private String detectedUrl;
    private List<FaceData> faceDatum;
    private LocalDateTime startDate;
    private QueueStatus status;
    private byte[] image;
    private String imageName;
    private long imageSize;
    private List<MultipartFile> files;
}
