package com.catchvbackend.api.FaceData.repository;

public interface FaceDataRepository {
    //얼굴 데이터를 받았을때의 필요한 태그들로 업로드의 필수항목을 정의

    void upload(FaceData faceData, String userEmail, String startDate);

}
