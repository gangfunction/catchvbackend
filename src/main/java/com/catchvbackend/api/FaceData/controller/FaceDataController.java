package com.catchvbackend.api.FaceData.controller;

import com.catchvbackend.api.FaceData.repository.TempFaceDataRepository;
import com.catchvbackend.api.FaceData.service.FaceDataService;
import com.catchvbackend.api.FaceData.service.FaceDataServiceDto;
import com.catchvbackend.api.FaceData.service.QueueStatus;
import com.catchvbackend.api.FaceData.service.ResultFaceData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.MediaTypes;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.catchvbackend.api.FaceData.service.FaceDataService.REST_TEMPLATE;
import static org.springframework.hateoas.MediaTypes.*;


@Slf4j
@RestController
@RequestMapping(value = "/image", produces = HAL_JSON)
public class FaceDataController {
    /**
     * 문제는 Repository를 직접 의존성 주입했었다는 것.
     * 컨트롤러 패키지에 Dto를 추가했었던 첫 개선점은 별 문제가 되지않지만, controller단에서
     * 로직에 관한 처리를 하는것이 바람직하지 않다고 판단했다.
     */
    private final FaceDataServiceDto serviceDto;

//    private final TempFaceDataRepository repository;
//    public FaceDataController(TempFaceDataRepository repository) {
//        this.repository = repository;
//    }

    public FaceDataController(FaceDataServiceDto dataServiceDto) {
        this.serviceDto = dataServiceDto;
    }

    @PostMapping(value="/request")
    public List<ResultFaceData> requestImage(@RequestBody String message) throws JSONException {
        return serviceDto.checkResult(FaceDataService.requestImageEmailExtraction(message));
    }

    @PostMapping(value="/result")
    public void resultJson(@RequestBody String resultData) {
        serviceDto.resultJsonProcessing(serviceDto, resultData);
    }

    @PostMapping(value = "/api")
    public void uploadImage(@RequestBody FaceDataServiceDto serviceDto, QueueStatus status) throws IOException {
        FaceDataServiceDto.uploadEvaluationLogic(serviceDto, status);
    }

    @GetMapping(value = "/responseCsv")
    public void responseCsv(){
        REST_TEMPLATE.getForEntity("http://localhost:5001/image/toCsv", String.class);
    }

    @PostMapping(value = "/downCsv")
    public void downCsvFile(@RequestBody String files){
        File file = new File(Arrays.toString(files.getBytes()));
    }
}