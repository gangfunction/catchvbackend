package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.face.QFaceData;
import com.catchvbackend.api.FaceData.service.FaceDataServiceDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RequestRepository {
    private final JPAQueryFactory queryFactory;
    private FaceDataServiceDto serviceDto;
    public void send(List<MultipartFile> files, String userEmail, LocalDateTime startDate, String raw_len){
        String url = "http://localhost:5001/image/api";
        serviceDto.sendServiceProcedure(files, url, userEmail, startDate, raw_len);
    }

    public void upload(Long id, byte[] list, String name, Long size, String userEmail){
        queryFactory
                .update(QFaceData.faceData)
                .set(QFaceData.faceData.id, id)
                .set(QFaceData.faceData.imageObject, list)
                .set(QFaceData.faceData.imageName, name)
                .set(QFaceData.faceData.imageSize, size)
                .set(QFaceData.faceData.userEmail, userEmail)
                .execute();
    }


}
