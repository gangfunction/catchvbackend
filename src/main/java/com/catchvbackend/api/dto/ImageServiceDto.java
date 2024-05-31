package com.catchvbackend.api.dto;

import com.catchvbackend.api.common.QueueStatus;
import com.catchvbackend.domain.face.FaceData;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ImageServiceDto {
    /**
     * 목적은 컨트롤러에서 객체들을 받아와서 서비스단에 넘겨주는것이다.
     * 방법은 컨트롤러에서 사용하는 속성 및 객체들에 대한 내용을 기술하고 필요에 따라 분리한다.
     */
    private String rawLen;
    private Integer detectCount;
    private String detectedUrl;
    private List<FaceData> faceDatum;
    private LocalDateTime startDate;

    private Long id;
    private String userEmail;
    private QueueStatus status;
    private byte[] image;
    private String imageName;
    private long imageSize;
    private List<MultipartFile> files;

    private RepositoryDto repositoryDto;

}
