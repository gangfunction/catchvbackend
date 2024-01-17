package com.catchvbackend.api.FaceData.service;


import com.catchvbackend.api.FaceData.repository.FaceDataRepositoryDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
public class FaceDataService {
    /*
    스프링 3.0부터 지원되었던 내장클레스 RestTemplate를 활용했습니다.
    변경여지가 없으므로 변수명은 대문자로 활용했습니다.
     */
    public static final RestTemplate REST_TEMPLATE;
    private final FaceDataRepositoryDto repositoryDto;


    /*
    변경여지가 없고 항상 활용되어야하므로 스태틱블록을 활용했습니다.
    */
    static{
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        factory.setBufferRequestBody(false);
        REST_TEMPLATE = new RestTemplate(factory);
    }

    public FaceDataService(FaceDataRepositoryDto repositoryDto) {
        this.repositoryDto = repositoryDto;
    }

    /*
    멀티파트 폼데이터를 활용해야 application/json에비해 대용량의 사진을 전송하기 용이했었습니다.
     */
    public static void sendService(String url, LinkedMultiValueMap<String, Object> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(map, headers);
        REST_TEMPLATE.postForObject(url, requestEntity, JsonNode.class);
    }

    public static ResponseEntity<HttpStatus> sendServiceProcedure(FaceDataRequestModel serviceRequestData) {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        try {
            FaceDataRequestModelMapping(serviceRequestData, map);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getStatusCode().value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        sendService(serviceRequestData.url(), map);
        return null;
    }

    private static void FaceDataRequestModelMapping(FaceDataRequestModel serviceRequestData, LinkedMultiValueMap<String, Object> map) {
        for (MultipartFile file : serviceRequestData.files()) {
            if (!file.isEmpty()) {
                map.add("files",  file.getResource());
                map.add("userEmail", serviceRequestData.userEmail());
                map.add("startDate", serviceRequestData.startDate());
                map.add("raw_len" , serviceRequestData.raw_len());
            }else{
                log.info("No files found");
            }
        }
    }

    public  void resultJsonProcessing(FaceDataServiceDto serviceDto, String resultData) {
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
            serviceDto.saveResult(videoCount,detectCount,userEmail,stringUrlList);

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

    public void addToWaitingList(byte[] image, String name, long size,List<MultipartFile> list, String userEmail, String startDate) throws IOException {
        for (MultipartFile ignored : list) {
//            FaceData faceData = new FaceData(file.getBytes(), file.getName(), file.getSize());
            repositoryDto.upload(image, name, size, userEmail, startDate);
        }// faceData 의 요소를 다시 뽑아봐야겠다. byte[] image, String name, long size

    }

}
