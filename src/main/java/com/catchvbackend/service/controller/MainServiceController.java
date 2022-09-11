package com.catchvbackend.service.controller;

import com.catchvbackend.service.SeviceRepository.Image.FaceData;
import com.catchvbackend.service.SeviceRepository.dao.FaceDataDaoJDBC;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/image")
public class MainServiceController {

    private final FaceDataDaoJDBC imageDaoJDBC;
    @Autowired
    public MainServiceController(FaceDataDaoJDBC imageDaoJDBC) {
        this.imageDaoJDBC = imageDaoJDBC;

    }
    private static final RestTemplate REST_TEMPLATE;
    static{
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        factory.setBufferRequestBody(false);
        REST_TEMPLATE = new RestTemplate(factory);
    }

    private ResponseEntity<?> send(List<MultipartFile> files, String userEmail,String startDate){
        String url = "http://localhost:5001/image/api";
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        JsonNode response;
        HttpStatus httpStatus = HttpStatus.CREATED;

        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    map.add("files",  file.getResource());
                }else{
                    log.info("No files found");
                }
            }
            map.add("userEmail", userEmail);
            map.add("startDate", startDate);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);


            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(map, headers);
            response = REST_TEMPLATE.postForObject(url, requestEntity, JsonNode.class);

        } catch (HttpStatusCodeException e) {
            HttpStatus errorHttpStatus = HttpStatus.valueOf(e.getStatusCode().value());
            String errorResponse = e.getResponseBodyAsString();
            return new ResponseEntity<>(errorResponse, errorHttpStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        log.info(String.valueOf(response));

        return new ResponseEntity<>(response, httpStatus);
    }
    @PostMapping(value="/receive")
    public void receiveMessage(@RequestBody String message){

        log.info(message);

        log.info("Received message");
    }
    @ResponseBody
    @PostMapping(value="/result")
    public void resultJson(@RequestBody HashMap<String, Object> resultData) throws ParseException {
        JSONParser jsonParser = new JSONParser(String.valueOf(resultData));
        JSONObject jsonObject = (JSONObject) jsonParser.parse();
        log.info(String.valueOf(resultData));
    }

    @PostMapping(value = "/api")
    public void uploadImage(@RequestParam("files") List<MultipartFile> faceDatalist,
                            @RequestParam("email") String userEmail, @RequestParam("startDate") String startDate){
        log.info("uploaded by"+userEmail);
        log.info("startDate"+startDate);
        //대기열 존재하지 않을시
        send(faceDatalist, userEmail,startDate);


        //만약 대기열이 존재할시
        MultipartFile multipartFile ;
        for (MultipartFile file  : faceDatalist) {
            FaceData faceData = new FaceData();
            multipartFile = file;
            try {
                faceData.setName(multipartFile.getName());
                faceData.setImage(multipartFile.getBytes());
                faceData.setSize(multipartFile.getSize());
                imageDaoJDBC.upload(faceData, userEmail, startDate);
            } catch (IOException e) {
                log.info("Exception" + e);
            }
        }
        }
}
