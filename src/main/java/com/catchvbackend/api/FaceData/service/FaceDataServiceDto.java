package com.catchvbackend.api.FaceData.service;

import com.catchvbackend.api.FaceData.repository.FaceData;
import com.catchvbackend.api.FaceData.repository.FaceDataRepositoryDto;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class FaceDataServiceDto {
    /**
     * 목적은 컨트롤러에서 객체들을 받아와서 서비스단에 넘겨주는것이다.
     * 방법은 컨트롤러에서 사용하는 속성 및 객체들에 대한 내용을 기술하고 필요에 따라 분리한다.
     */
    private List<MultipartFile> list;
    private String userEmail;
    private String startDate;
    private String rawLen;
    private static FaceDataRepositoryDto repositoryDto;
    private static FaceDataService service;
    private QueueStatus status;

    public static void uploadEvaluationLogic(FaceDataServiceDto serviceDto, QueueStatus status) throws IOException {
        if(status != null){
            if(Objects.equals(status.getCodes(), "500")){
                    service.addToWaitingList(serviceDto, serviceDto.getList(), serviceDto.getUserEmail(), serviceDto.getStartDate());
            }
            repositoryDto.send(serviceDto.getList(), serviceDto.getUserEmail(), serviceDto.getStartDate(), serviceDto.getRawLen());
        }
    }

    public List<ResultFaceData> checkResult(String userEmail) {
        return repositoryDto.checkResult(userEmail);
    }

    public void resultJsonProcessing(FaceDataServiceDto serviceDto, String resultData) {
        service.resultJsonProcessing(serviceDto, resultData);
    }

    public void saveResult(int videoCount, int detectCount, String userEmail, String stringUrlList) {
        repositoryDto.saveResult(videoCount,detectCount,userEmail,stringUrlList);
    }


    public void upload(FaceData faceData, String userEmail, String startDate) {
        repositoryDto.upload(faceData,userEmail,startDate);
    }


}
