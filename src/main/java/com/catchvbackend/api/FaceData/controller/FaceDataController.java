package com.catchvbackend.api.FaceData.controller;

import com.catchvbackend.api.FaceData.service.ResultFaceData;
import com.catchvbackend.api.FaceData.repository.FaceDataRepositoryImpl;
import com.catchvbackend.api.FaceData.service.FaceDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.catchvbackend.api.FaceData.service.FaceDataService.REST_TEMPLATE;


@Slf4j
@RestController
@RequestMapping("/image")
public class FaceDataController {
    /**
     * 문제는 Repository를 직접 의존성 주입했었다는 것.
     * 컨트롤러 패키지에 Dto를 추가했었던 첫 개선점은 별 문제가 되지않지만, controller단에서
     * 로직에 관한 처리를 하는것이 바람직하지 않다고 판단했다.
     */
    private final FaceDataRepositoryImpl imageDaoJDBC;

    @Autowired
    public FaceDataController(FaceDataRepositoryImpl imageDaoJDBC) {
        this.imageDaoJDBC = imageDaoJDBC;
    }

    @PostMapping(value="/request")
    public List<ResultFaceData> requestImage(@RequestBody String message) throws JSONException {
        String userEmail = FaceDataService.requestImageEmailExtraction(message);
        return imageDaoJDBC.checkResult(userEmail);
    }

    @PostMapping(value="/result")
    public void resultJson(@RequestBody String resultData) {
        log.info("processing started");
        FaceDataService.resultJsonProcessing(imageDaoJDBC, resultData);
    }

    @PostMapping(value = "/api")
    public void uploadImage(@RequestParam("files") List<MultipartFile> faceDatalist,
                            @RequestParam("email") String userEmail,
                            @RequestParam("startDate") String startDate,
                            @RequestParam("raw_len") String raw_len) throws IOException {
        //대기열 존재하지 않을시
        imageDaoJDBC.send(faceDatalist, userEmail,startDate,raw_len);
        //만약 대기열이 존재할시
        FaceDataService.addToWaitingList(imageDaoJDBC, faceDatalist, userEmail, startDate);
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