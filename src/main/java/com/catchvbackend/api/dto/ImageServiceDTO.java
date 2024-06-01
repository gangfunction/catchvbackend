package com.catchvbackend.api.dto;

import com.catchvbackend.api.common.QueueStatus;
import com.catchvbackend.domain.face.FaceData;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ImageServiceDTO는 컨트롤러에서 객체들을 받아와서 서비스단에 넘겨주는 역할을 합니다.
 * 이 클래스는 불변 객체로 설계되어 데이터의 일관성을 유지합니다.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class ImageServiceDTO {

    /**
     * 원본 길이
     */
    private final String rawLen;

    /**
     * 감지된 개수
     */
    private final Integer detectCount;

    /**
     * 감지된 URL
     */
    private final String detectedUrl;

    /**
     * 얼굴 데이터 목록
     */
    private final List<FaceData> faceDatum;

    /**
     * 시작 날짜
     */
    private final LocalDateTime startDate;

    /**
     * ID
     */
    private final Long id;

    /**
     * 사용자 이메일
     */
    private final String userEmail;

    /**
     * 큐 상태
     */
    private final QueueStatus status;

    /**
     * 이미지 데이터
     */
    private final byte[] image;

    /**
     * 이미지 이름
     */
    private final String imageName;

    /**
     * 이미지 크기
     */
    private final long imageSize;

    /**
     * 파일 목록
     */
    private final List<MultipartFile> files;
    
}