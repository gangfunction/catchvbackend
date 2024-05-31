package com.catchvbackend.api.repository;

import com.catchvbackend.api.FaceData.domain.QImageResult;
import com.catchvbackend.api.FaceData.domain.face.QFaceData;
import com.catchvbackend.domain.ImageResult;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomImageRepositoryImpl implements CustomImageRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void saveResult(ImageResult imageResult) {
        queryFactory.insert(QImageResult.imageResult)
            .set(QImageResult.imageResult.videoCount, imageResult.getVideoCount())
            .set(QImageResult.imageResult.detectCount, imageResult.getDetectCount())
            .set(QImageResult.imageResult.userEmail, imageResult.getUserEmail())
            .set(QImageResult.imageResult.detectedUrl, imageResult.getDetectedUrl())
            .execute();
    }

    // Methods from CustomImageRepositoryImpl


    @Override
    public void upload(Long id, byte[] list, String name, Long size, String userEmail) {
        queryFactory.update(QFaceData.faceData)
            .set(QFaceData.faceData.id, id)
            .set(QFaceData.faceData.imageObject, list)
            .set(QFaceData.faceData.imageName, name)
            .set(QFaceData.faceData.imageSize, size)
            .set(QFaceData.faceData.userEmail, userEmail)
            .execute();
    }

}
