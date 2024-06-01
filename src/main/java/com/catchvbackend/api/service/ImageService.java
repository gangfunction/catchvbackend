package com.catchvbackend.api.service;

import com.catchvbackend.api.dto.ImageServiceDTO;
import com.catchvbackend.api.repository.FaceDataRepository;
import com.catchvbackend.api.repository.ImageRepository;
import com.catchvbackend.domain.ImageResponse;
import com.catchvbackend.domain.face.FaceData;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final FaceDataRepository faceDataRepository;
    private final RestTemplate restTemplate;
    private final ImageProcessingQueue imageProcessingQueue;


    public void faceDataRequestModelMapping(List<MultipartFile> files, LinkedMultiValueMap<String, Object> map, ImageServiceDTO imageServiceDto) {
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


    public void resultJsonProcessing(String resultData) {
        try {
            JSONObject jsonObject = new JSONObject(resultData);
            int videoCount = jsonObject.getInt("total_inspected_video_count");
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject processUserInfo = result.getJSONObject(0);
            String userEmail = processUserInfo.getString("requested_user_email");
            JSONArray processUrlLists = processUserInfo.getJSONArray("urls");
            List<String> urlList = new ArrayList<>();
            urlListMapping(processUrlLists, urlList);
            int detectCount = urlList.size();
            ImageResponse createdImageResponse = ImageResponse.createServiceResult(videoCount, detectCount, userEmail, urlList);
            saveResult(createdImageResponse);
        } catch (Exception e) {
            log.error("Error processing result json", e);
        }
    }


    @Transactional
    public void saveResult(ImageResponse imageResponseData) {
        imageRepository.saveResult(imageResponseData);
    }


    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processQueue() {
        while (!imageProcessingQueue.isEmpty()) {
            try {
                ImageServiceDTO imageServiceDto = imageProcessingQueue.takeFromQueue();
                processImageServiceDTO(imageServiceDto);
            } catch (InterruptedException e) {
                log.error("Error processing queue", e);
                Thread.currentThread().interrupt();
            }
        }
    }


    @Transactional
    public void addToWaitingList(ImageServiceDTO imageServiceDto) {
        imageProcessingQueue.addToQueue(imageServiceDto);
        log.info("Added to queue: {}", imageServiceDto.getId());
    }


    @Transactional
    public void processImageServiceDTO(ImageServiceDTO imageServiceDto) {
        Long id = imageServiceDto.getId();
        List<FaceData> datum = imageServiceDto.getFaceDatum();
        String imageName = imageServiceDto.getImageName();
        long imageSize = imageServiceDto.getImageSize();
        String userEmail = imageServiceDto.getUserEmail();
        if (datum != null) {
            for (FaceData data : datum) {
                if (data != null && data.getImageObject() != null) {
                    faceDataRepository.upload(id, new BASE64DecodedMultipartFile(data.getImageObject(), imageName), imageName, imageSize, userEmail);
                } else {
                    log.warn("FaceData or imageObject is null for id: {}", id);
                }
            }
        } else {
            log.warn("FaceDatum list is null for id: {}", id);
        }
    }


    @Transactional
    public void uploadEvaluationLogic(ImageServiceDTO imageServiceDto) {
        if ("500".equals(imageServiceDto.getStatus().getCodes())) {
            addToWaitingList(imageServiceDto);
        }
        sendFiles(imageServiceDto.getFiles(), imageServiceDto);
    }


    public void sendFiles(List<MultipartFile> files, ImageServiceDTO imageServiceDto) {
        String url = "http://localhost:5001/image/api";
        sendServiceProcedure(files, url, imageServiceDto);
    }


    public List<ImageResponse> checkResult(String userEmail) {
        return imageRepository.findByUserEmail(userEmail);
    }


    public void sendService(String url, LinkedMultiValueMap<String, Object> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        restTemplate.postForObject(url, requestEntity, JsonNode.class);
    }

    /**
     * Sends the service procedure.
     */
    public ResponseEntity<?> sendServiceProcedure(List<MultipartFile> files, String url, ImageServiceDTO imageServiceDto) {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        faceDataRequestModelMapping(files, map, imageServiceDto);
        try {
            sendService(url, map);
            return ResponseEntity.ok().build();
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getStatusCode().value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Maps the URL list from the JSON array.
     */
    private void urlListMapping(JSONArray processUrlLists, List<String> urlList) {
        for (int i = 0; i < processUrlLists.length(); i++) {
            String tempString = processUrlLists.getString(i).replaceAll("\"", "");
            log.info(tempString);
            urlList.add(tempString);
        }
    }
}