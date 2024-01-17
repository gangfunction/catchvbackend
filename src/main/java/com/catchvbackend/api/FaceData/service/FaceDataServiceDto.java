package com.catchvbackend.api.FaceData.service;

import com.catchvbackend.api.FaceData.repository.FaceDataRepositoryDto;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class FaceDataServiceDto {
    /**
     * 목적은 컨트롤러에서 객체들을 받아와서 서비스단에 넘겨주는것이다.
     * 방법은 컨트롤러에서 사용하는 속성 및 객체들에 대한 내용을 기술하고 필요에 따라 분리한다.
     */
    private List<MultipartFile> fileList;
    private String userEmail;
    private String startDate;
    private String rawLen;
    private FaceDataRepositoryDto repositoryDto;
    private FaceDataService service;
    private QueueStatus status;
    private byte[] image;
    private String imageName;
    private long imageSize;



    //FaceDataServiceDto serviceDto, QueueStatus status
    public void uploadEvaluationLogic() throws IOException {
        if(status != null && "500".equals(status.getCodes())){
            service.addToWaitingList(image, imageName, imageSize, fileList, userEmail, startDate);
        }
        repositoryDto.send(fileList, userEmail, startDate, rawLen);

    }


    /**
     *            service.addToWaitingList(new FaceDataRequestAddWaiting(this, list, userEmail, startDate));
     *         }
     *         repositoryDto.send(list, userEmail, startDate, rawLen);
     *     }
     */

    public List<ResultFaceData> checkResult(String userEmail) {
        return repositoryDto.checkResult(this.userEmail);
    }

    public void resultJsonProcessing(String resultData){
        service.resultJsonProcessing(this, resultData);
    }

    public void saveResult(int videoCount, int detectCount, String userEmail, String stringUrlList) {
        repositoryDto.saveResult(videoCount,detectCount,userEmail,stringUrlList);
    }


}
