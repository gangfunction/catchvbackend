package com.catchvbackend.api.mainService.controller;


import com.catchvbackend.api.mainService.repository.MainServiceRepositoryDto;
import com.catchvbackend.api.mainService.repository.ResultData;
import com.catchvbackend.api.mainService.repository.dao.FaceDataDaoImplTest;
import com.catchvbackend.api.mainService.service.MainServiceLayerTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.catchvbackend.api.mainService.service.MainServiceLayerTest.*;


@Slf4j
@RestController
@RequestMapping("/image")
class MainServiceControllerTest {

    private FaceDataDaoImplTest imageDaoJDBCTest;
    private MainServiceControllerTestDto ControllerDto;



    @Autowired
    public void MainServiceController(FaceDataDaoImplTest imageDaoJDBC) {
        this.imageDaoJDBCTest = imageDaoJDBC;
    }

    @PostMapping(value="/request")
    public List<ResultData> requestImage(@RequestBody String message) {
        try {
            return imageDaoJDBCTest.checkResult(requestImageEmailExtraction(message));
        } catch (org.json.JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value="/result")
    public void resultJson(@RequestBody String resultData) {
        resultJsonProcessing(imageDaoJDBCTest, resultData);
    }
    /*
    대기열의 존재여부를 확정하는 로직이 존재하지않습니다.
    */
    @PostMapping(value = "/api")
    public void uploadImage(MainServiceRepositoryDto dto) throws IOException {
        Stream.builder().add(send(ControllerDto))
                .add(Objects.requireNonNull(addToWaitingList(dto)));
    }



    @GetMapping(value = "/responseCsv")
    public void responseCsv(){
        REST_TEMPLATE.getForEntity( "http://localhost:5001/image/toCsv", String.class);
    }
    /*
    다운로드에 대한 구현은 아직되어있지 않았었다.
     */
    @PostMapping(value = "/downCsv")
    public void downCsvFile(@RequestBody String files){
        File file = new File(Arrays.toString(files.getBytes()));
    }
}