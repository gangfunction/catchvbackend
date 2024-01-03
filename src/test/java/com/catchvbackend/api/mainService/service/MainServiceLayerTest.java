package com.catchvbackend.api.mainService.service;

import com.catchvbackend.api.mainService.controller.MainServiceControllerTestDto;
import com.catchvbackend.api.mainService.repository.MainServiceRepositoryDto;
import com.catchvbackend.api.mainService.repository.dao.FaceDataDaoImplTest;
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
import java.util.function.Consumer;

@Slf4j
@Service
public class MainServiceLayerTest {
    /*
스프링 3.0부터 지원되었던 내장클레스 RestTemplate를 활용했습니다.
변경여지가 없으므로 변수명은 대문자로 활용했습니다.
 */
    public static final RestTemplate REST_TEMPLATE;
    static MainServiceControllerTestDto controllerTestDto;

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
    public static ResponseEntity<HttpStatus> send(MainServiceControllerTestDto dto){
        String url = "http://localhost:5001/image/api";
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseEntity<HttpStatus> errorHttpStatus = MainServiceLayerTest.sendServiceProcedure(dto.files, dto.userEmail, dto.startDate, dto.raw_len, url, map);
        if (errorHttpStatus != null) return errorHttpStatus;
        return new ResponseEntity<>(httpStatus);
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
            /*
            매핑을 통해서 User 정보를 매핑했습니다.
             */
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

    public static void resultJsonProcessing(FaceDataDaoImplTest faceDataDao, String resultData) {
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

    public static Consumer<? super Object> addToWaitingList(MainServiceRepositoryDto dto) throws IOException {
        for (MultipartFile file  : controllerTestDto.files) {
            FaceDataDaoImplTest faceData = new FaceDataDaoImplTest();
            faceData.upload( dto,
                    controllerTestDto.userEmail,
                    controllerTestDto.startDate);
        }
        return null;
    }

}