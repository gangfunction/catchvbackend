package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.QResult;
import com.catchvbackend.api.FaceData.domain.Result;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ResultRepository {
    private final JPAQueryFactory queryFactory;

    public List<Result> checkResult(String userEmail) {
        return queryFactory.selectFrom(QResult.result)
                .where(QResult.result.userEmail.eq(userEmail))
                .fetch();
    }
    public void saveResult(Result result) {
        queryFactory.insert(QResult.result)
                .set(QResult.result.videoCount, result.getVideoCount())
                .set(QResult.result.detectCount, result.getDetectCount())
                .set(QResult.result.userEmail, result.getUserEmail())
                .set(QResult.result.detectedUrl, result.getDetectedUrl())
                .execute();
    }


}
