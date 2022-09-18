package com.catchvbackend.api.service.repository.dao;

import com.catchvbackend.api.service.repository.FaceData;

public interface FaceDataDao {

    void upload(FaceData faceData, String userEmail, String startDate);

}
