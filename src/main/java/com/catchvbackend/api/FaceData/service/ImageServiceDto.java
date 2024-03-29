package com.catchvbackend.api.FaceData.service;

import com.catchvbackend.api.FaceData.domain.ImageResult;
import com.catchvbackend.api.FaceData.domain.face.FaceData;
import com.catchvbackend.api.FaceData.repository.RepositoryDto;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ImageServiceDto {
    /**
     * 목적은 컨트롤러에서 객체들을 받아와서 서비스단에 넘겨주는것이다.
     * 방법은 컨트롤러에서 사용하는 속성 및 객체들에 대한 내용을 기술하고 필요에 따라 분리한다.
     */
    private String rawLen;
    private Integer detectCount;
    private String detectedUrl;
    private List<FaceData> faceDatum;
    private LocalDateTime startDate;

    private Long id;
    private String userEmail;
    private QueueStatus status;
    private byte[] image;
    private String imageName;
    private long imageSize;
    private List<MultipartFile> files;

    private RepositoryDto repositoryDto;
    private FaceDataService service;
    private ImageResultService imageResultService;
    private ImageRequestService imageRequestService;


    public ResponseEntity<?> sendServiceProcedure(List<MultipartFile> files, String url) {
        return service.sendServiceProcedure(files, url);
    }


    //ImageServiceDto serviceDto, QueueStatus status
    public  void uploadEvaluationLogic() {
        if (status != null && "500".equals(status.getCodes())) {
            repositoryDto.addToWaitingList(id,faceDatum, imageName, imageSize, userEmail, startDate);
        }
        repositoryDto.send(files, userEmail, startDate, rawLen);

        //take status from class common
        //if status is 500, add to waiting list
        //else send to repository


    }




    public List<ImageResult> checkResult(String userEmail) {
        return repositoryDto.checkResult(userEmail);
    }

    public void resultJsonProcessing(String requestUserEmail) {
        imageResultService.resultJsonProcessing(requestUserEmail);
    }



}
