package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.Result;
import com.catchvbackend.api.FaceData.domain.face.FaceData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class FaceDataRepositoryDto {
    private FaceDataRepository repository;
    private RequestRepository requestRepository;
    private ResultRepository resultRepository;

    //checkresult
    public List<Result> checkResult(String userEmail) {
        return resultRepository.checkResult(userEmail);
    }


    public  void send(List<MultipartFile> files, String userEmail, LocalDateTime startDate, String rawLen) {
        requestRepository.send(files,userEmail,startDate,rawLen);
    }


    public void addToWaitingList(Long id, List<FaceData> datum, String imageName, long imageSize, String userEmail, LocalDateTime startDate) {
        for (FaceData data : datum) {
            requestRepository.upload(id, data.getImageObject(), imageName, imageSize, userEmail, startDate);
        }// faceData 의 요소를 다시 뽑아봐야겠다. byte[] image, String name, long size

    }
}
