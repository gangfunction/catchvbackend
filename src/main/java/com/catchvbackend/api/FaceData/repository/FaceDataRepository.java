package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.face.FaceData;
import com.catchvbackend.api.FaceData.service.FaceDataRequestModel;
import com.catchvbackend.api.FaceData.service.FaceDataServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FaceDataRepository {

    private final EntityManager em;


    public void save(FaceData facedata){
        if(facedata.getId()==null){
            em.persist(facedata);
        }else{
            em.merge(facedata);
        }
    }
    public void send(List<FaceData> files, String userEmail, LocalDateTime startDate, String raw_len){
        String url = "http://localhost:5001/image/api";
        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseEntity<HttpStatus> errorHttpStatus = FaceDataServiceDto
                .sendServiceProcedure(new FaceDataRequestModel(files, userEmail, startDate, raw_len, url));
        if (errorHttpStatus != null) return;
        new ResponseEntity<>(httpStatus);
    }









}


