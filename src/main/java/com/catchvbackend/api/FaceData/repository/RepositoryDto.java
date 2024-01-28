package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.ImageResult;
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
public class RepositoryDto {
    private ImageRequestRepository imageRequestRepository;
    private ImageResultRepository imageResultRepository;

    //checkresult
    public List<ImageResult> checkResult(String userEmail) {
        return imageResultRepository.checkResult(userEmail);
    }


    public  void send(List<MultipartFile> files, String userEmail, LocalDateTime startDate, String rawLen) {
        imageRequestRepository.send(files,userEmail,startDate,rawLen);
    }


    public void addToWaitingList(Long id, List<FaceData> datum, String imageName, long imageSize, String userEmail, LocalDateTime startDate) {
        for (FaceData data : datum) {
            imageRequestRepository.upload(id, data.getImageObject(), imageName, imageSize, userEmail);
        }// faceData 의 요소를 다시 뽑아봐야겠다. byte[] image, String name, long size

    }
}
