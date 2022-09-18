package com.catchvbackend.api.service.controller;

import com.catchvbackend.api.service.repository.FaceData;
import com.catchvbackend.api.service.repository.ResultData;
import com.catchvbackend.api.service.repository.dao.FaceDataDaoImpl;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/image")
public class MainServiceController {

    private final FaceDataDaoImpl imageDaoJDBC;
    private static final RestTemplate REST_TEMPLATE;
    static{
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        factory.setBufferRequestBody(false);
        REST_TEMPLATE = new RestTemplate(factory);
    }

    @Autowired
    public MainServiceController(FaceDataDaoImpl imageDaoJDBC) {
        this.imageDaoJDBC = imageDaoJDBC;

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
        log.info("Received message");
    }
    @PostMapping(value="/request")
    @ResponseBody
    public List<ResultData> requestImage(@RequestBody String message) throws JSONException {
        HashMap<String, String> dict= new HashMap<>();
        JSONObject json = new JSONObject(String.valueOf(message));
        Iterator i = json.keys();
        while(i.hasNext()){
            String k = i.next().toString();
            dict.put(k, json.getString(k));
        }
        String userEmail = dict.get("userEmail");
        List<ResultData> results = imageDaoJDBC.checkResult(userEmail);
        return results;

    }

    @ResponseBody
    @PostMapping(value="/result")
    public void resultJson(@RequestBody String resultData) {
        log.info("processing started");
        resultJsonProcessing(resultData);

    }
    private void resultJsonProcessing(String resultData) {
        try{
            JSONObject jsonObject = new JSONObject(resultData);
            int videoCount = Integer.parseInt(jsonObject.getString("total_inspected_video_count"));
            JSONArray result= jsonObject.getJSONArray("result");
            JSONObject processUserInfo = (JSONObject) result.get(0);
            String userEmail=processUserInfo.getString("requested_user_email");
            JSONArray processUrlLists=processUserInfo.getJSONArray("urls");
            ArrayList<String> urlList= new ArrayList<>();
            if(processUrlLists != null){
                for(int i=0;i<processUrlLists.length();i++){
                    String tempString= processUrlLists.getString(i);
                    String ptempString = tempString.replaceAll("\"","");
                    log.info(ptempString);
                    urlList.add(ptempString);
                }
            }
            int detectCount =urlList.size();
            String stringUrlList=urlList.toString();
            imageDaoJDBC.saveResult(videoCount,detectCount,userEmail,stringUrlList);

        }catch(Exception e){
            log.error("Error processing result json",e);
        }

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

    @GetMapping(value = "/responseCsv")
    public void responseCsv(){
        String url = "http://localhost:5001/image/toCsv";
        REST_TEMPLATE.getForEntity(url, String.class);
    }

    @PostMapping(value = "/downCsv")
    public void downCsvFile(@RequestBody String files){
        File file = new File(Arrays.toString(files.getBytes()));
    }
}