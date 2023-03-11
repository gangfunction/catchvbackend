package com.catchvbackend.api.mainService.service;


import com.catchvbackend.api.mainService.repository.FaceData;
import com.catchvbackend.api.mainService.repository.dao.FaceDataDaoImpl;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


@Slf4j
@Service
public class mainServiceLayer {

    private static final RestTemplate REST_TEMPLATE;

    static{
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        factory.setBufferRequestBody(false);
        REST_TEMPLATE = new RestTemplate(factory);
    }
    public static void sendService(String url, LinkedMultiValueMap<String, Object> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(map, headers);
        REST_TEMPLATE.postForObject(url, requestEntity, JsonNode.class);
    }

    public static ResponseEntity<HttpStatus> sendServiceProcedure(List<MultipartFile> files, String userEmail, String startDate, String raw_len, String url, LinkedMultiValueMap<String, Object> map) {
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
            map.add("raw_len" , raw_len);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getStatusCode().value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        sendService(url, map);
        return null;
    }

    public static void resultJsonProcessing(FaceDataDaoImpl faceDataDao, String resultData) {
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
            faceDataDao.saveResult(videoCount,detectCount,userEmail,stringUrlList);

        }catch(Exception e){
            log.error("Error processing result json",e);
        }

    }

    public static String requestImageEmailExtraction(String message) throws JSONException {
        HashMap<String, String> dict= new HashMap<>();
        JSONObject json = new JSONObject(String.valueOf(message));
        Iterator<?> i = json.keys();
        while(i.hasNext()){
            String k = i.next().toString();
            dict.put(k, json.getString(k));
        }
        return dict.get("userEmail");
    }

    public static void addToWaitingList(FaceDataDaoImpl faceDataDao, List<MultipartFile> faceDatalist, String userEmail, String startDate) {
        for (MultipartFile file  : faceDatalist) {
            FaceData faceData = new FaceData();
            try {
                faceData.setName(file.getName());
                faceData.setImage(file.getBytes());
                faceData.setSize(file.getSize());
                faceDataDao.upload(faceData, userEmail, startDate);
            } catch (IOException e) {
                log.info("Exception" + e);
            }
        }
    }
}
