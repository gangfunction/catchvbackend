package com.catchvbackend.api.FaceData.repository;

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


    public  void send(List<FaceData> faceDatum, String userEmail, LocalDateTime startDate, String rawLen) {
        repository.send(faceDatum,userEmail,startDate,rawLen);
    }


    public void addToWaitingList(byte[] image, String imageName, long imageSize,List<MultipartFile> imageList, String userEmail, LocalDateTime startDate) {
        for (MultipartFile ignored : imageList) {
            requestRepository.upload(image, imageName, imageSize, userEmail, startDate);
        }// faceData 의 요소를 다시 뽑아봐야겠다. byte[] image, String name, long size

    }
}
