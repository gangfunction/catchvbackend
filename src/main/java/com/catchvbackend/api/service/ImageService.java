package com.catchvbackend.api.service;

import com.catchvbackend.api.dto.ImageServiceDto;
import com.catchvbackend.api.repository.CustomImageRepositoryImpl;
import com.catchvbackend.api.repository.ImageRepository;
import com.catchvbackend.domain.ImageResult;
import com.catchvbackend.domain.face.FaceData;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {
    private final CustomImageRepositoryImpl customImageRepositoryImpl;
    private final ImageRepository imageRepository;
    private ImageServiceDto imageServiceDto;

    public void faceDataRequestModelMapping(List<MultipartFile> files, LinkedMultiValueMap<String, Object> map) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                map.add("files", file.getResource());
                map.add("userEmail", imageServiceDto.getUserEmail());
                map.add("startDate", imageServiceDto.getStartDate());
                map.add("raw_len", imageServiceDto.getRawLen());
            } else {
                log.info("No files found");
            }
        }
    }

    public static String requestImageEmailExtraction(String userEmail) throws JSONException {
        HashMap<String, String> dict = new HashMap<>();
        JSONObject json = new JSONObject(String.valueOf(userEmail));
        Iterator<?> i = json.keys();
        requestImageEmailFind(i, dict, json);
        return dict.get("userEmail");
    }

    private static void requestImageEmailFind(Iterator<?> i, HashMap<String, String> dict, JSONObject json) {
        while (i.hasNext()) {
            String k = i.next().toString();
            dict.put(k, json.getString(k));
        }
    }

    public void resultJsonProcessing(String resultData) {
        try {
            JSONObject jsonObject = new JSONObject(resultData);
            int videoCount = Integer.parseInt(jsonObject.getString("total_inspected_video_count"));
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject processUserInfo = (JSONObject) result.get(0);
            String userEmail = processUserInfo.getString("requested_user_email");
            JSONArray processUrlLists = processUserInfo.getJSONArray("urls");
            ArrayList<String> urlList = new ArrayList<>();
            if (processUrlLists != null) {
                urlListMapping(processUrlLists, urlList);
            }
            int detectCount = urlList.size();
            ImageResult createdImageResult = ImageResult.createServiceResult(videoCount, detectCount, userEmail, urlList);
            saveResult(createdImageResult);
        } catch (Exception e) {
            log.error("Error processing result json", e);
        }
    }

    private static void urlListMapping(JSONArray processUrlLists, ArrayList<String> urlList) {
        for (int i = 0; i < processUrlLists.length(); i++) {
            String tempString = processUrlLists.getString(i);
            String ptempString = tempString.replaceAll("\"", "");
            log.info(ptempString);
            urlList.add(ptempString);
        }
    }

    @Transactional
    public void saveResult(ImageResult imageResultData) {
        customImageRepositoryImpl.saveResult(imageResultData);
    }


    @Transactional
    public void addToWaitingList(Long id, List<FaceData> datum, String imageName, long imageSize, String userEmail, LocalDateTime startDate) {
        for (FaceData data : datum) {
            customImageRepositoryImpl.upload(id, data.getImageObject(), imageName, imageSize, userEmail);
        }
    }
    @Transactional
    public void uploadEvaluationLogic(ImageServiceDto imageServiceDto) {
        if (imageServiceDto.getStatus() != null && "500".equals(imageServiceDto.getStatus().getCodes())) {
            addToWaitingList(imageServiceDto.getId(),
                imageServiceDto.getFaceDatum(), imageServiceDto.getImageName(), imageServiceDto.getImageSize(), imageServiceDto.getUserEmail(), imageServiceDto.getStartDate());
        }
        send(imageServiceDto.getFiles());
    }
    @Transactional
    public void send(List<MultipartFile> files) {
        sendServiceProcedure(files, "http://localhost:5001/image/api");
    }

    public List<ImageResult> checkResult(String userEmail) {
        return imageRepository.findByUserEmail(userEmail);
    }

    public static void sendService(String url, LinkedMultiValueMap<String, Object> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity
            = new HttpEntity<>(map, headers);
        CustomRestTemplate.REST_TEMPLATE.postForObject(url, requestEntity, JsonNode.class);
    }
    public ResponseEntity<?> sendServiceProcedure(List<MultipartFile> files, String url) {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        faceDataRequestModelMapping(files, map);
        try {
            sendService(url, map);
            return ResponseEntity.ok().build();
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getStatusCode().value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
