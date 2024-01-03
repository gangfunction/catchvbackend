package com.catchvbackend.api.mainService.controller;

import com.catchvbackend.api.mainService.repository.ResultData;
import com.catchvbackend.api.mainService.repository.dao.FaceDataDaoImpl;
import com.catchvbackend.api.mainService.service.mainServiceLayer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/image")
public class MainServiceController {

    private final FaceDataDaoImpl imageDaoJDBC;

    @Autowired
    public MainServiceController(FaceDataDaoImpl imageDaoJDBC) {
        this.imageDaoJDBC = imageDaoJDBC;
    }

    private ResponseEntity<HttpStatus> send(List<MultipartFile> files, String userEmail,String startDate, String raw_len){
        String url = "http://localhost:5001/image/api";
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseEntity<HttpStatus> errorHttpStatus = mainServiceLayer.sendServiceProcedure(files, userEmail, startDate, raw_len, url, map);
        if (errorHttpStatus != null) return errorHttpStatus;
        return new ResponseEntity<>(httpStatus);
    }

    @PostMapping(value="/request")
    public List<ResultData> requestImage(@RequestBody String message) throws JSONException {
        String userEmail = mainServiceLayer.requestImageEmailExtraction(message);
        return imageDaoJDBC.checkResult(userEmail);
    }

    @PostMapping(value="/result")
    public void resultJson(@RequestBody String resultData) {
        log.info("processing started");
        mainServiceLayer.resultJsonProcessing(imageDaoJDBC, resultData);
    }

    @PostMapping(value = "/api")
    public void uploadImage(@RequestParam("files") List<MultipartFile> faceDatalist,
                            @RequestParam("email") String userEmail,
                            @RequestParam("startDate") String startDate,
                            @RequestParam("raw_len") String raw_len) throws IOException {
        //대기열 존재하지 않을시
        send(faceDatalist, userEmail,startDate,raw_len);
        //만약 대기열이 존재할시
        mainServiceLayer.addToWaitingList(imageDaoJDBC, faceDatalist, userEmail, startDate);
    }

    @GetMapping(value = "/responseCsv")
    public void responseCsv(){
//        String url = "http://localhost:5001/image/toCsv";
//        REST_TEMPLATE.getForEntity(url, String.class);
    }

    @PostMapping(value = "/downCsv")
    public void downCsvFile(@RequestBody String files){
        File file = new File(Arrays.toString(files.getBytes()));
    }
}