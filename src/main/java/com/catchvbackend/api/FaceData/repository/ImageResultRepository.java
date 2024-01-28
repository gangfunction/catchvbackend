package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.ImageResult;
import com.catchvbackend.api.FaceData.domain.QImageResult;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.catchvbackend.api.FaceData.domain.QImageResult.*;

@Repository
@AllArgsConstructor
public class ImageResultRepository {
    private final JPAQueryFactory queryFactory;

    public List<ImageResult> checkResult(String userEmail) {
        return queryFactory.selectFrom(imageResult)
                .where(imageResult.userEmail.eq(userEmail))
                .fetch();
    }
    public void saveResult(ImageResult imageResult) {
        long execute = queryFactory.insert(QImageResult.imageResult)
                .set(QImageResult.imageResult.videoCount, imageResult.getVideoCount())
                .set(QImageResult.imageResult.detectCount, imageResult.getDetectCount())
                .set(QImageResult.imageResult.userEmail, imageResult.getUserEmail())
                .set(QImageResult.imageResult.detectedUrl, imageResult.getDetectedUrl())
                .execute();
    }


}
