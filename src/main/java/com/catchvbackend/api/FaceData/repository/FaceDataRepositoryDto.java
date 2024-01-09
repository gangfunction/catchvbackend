package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.service.ResultFaceData;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Component
@Getter
@Setter
public class FaceDataRepositoryDto {
    private FaceDataRepository repository;

    public List<ResultFaceData> checkResult(String userEmail) {
        return repository.checkResult(userEmail);
    }

    public  void send(List<MultipartFile> faceDatalist, String userEmail, String startDate, String rawLen) {
        repository.send(faceDatalist,userEmail,startDate,rawLen);
    }
    public void saveResult(int videoCount, int detectCount, String userEmail, String urlList){
        repository.saveResult(videoCount, detectCount, userEmail, urlList);
    }

    public void upload(FaceData faceData, String userEmail, String startDate) {
        repository.upload(faceData,userEmail,startDate);
    }
}