package com.catchvbackend.api.mainService.repository.dao;

import com.catchvbackend.api.mainService.repository.FaceData;

public interface FaceDataDao {

    void upload(FaceData faceData, String userEmail, String startDate);

}
