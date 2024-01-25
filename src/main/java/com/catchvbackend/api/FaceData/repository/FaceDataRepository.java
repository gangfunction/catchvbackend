package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.face.FaceData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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


}


