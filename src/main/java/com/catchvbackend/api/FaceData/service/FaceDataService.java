package com.catchvbackend.api.FaceData.service;


import com.catchvbackend.api.FaceData.domain.Request;
import com.catchvbackend.api.FaceData.repository.FaceDataRepositoryDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FaceDataService {
    private RequestService requestService;
    /*
    멀티파트 폼데이터를 활용해야 application/json에비해 대용량의 사진을 전송하기 용이했었습니다.
     */
    public static void sendService(String url, LinkedMultiValueMap<String, Object> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(map, headers);
        CustomRestTemplate.REST_TEMPLATE.postForObject(url, requestEntity, JsonNode.class);
    }

    public ResponseEntity<HttpStatus> sendServiceProcedure(List<MultipartFile> files, String url) {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        try {
            requestService.faceDataRequestModelMapping(files, map);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getStatusCode().value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        sendService(url, map);
        return null;
    }





}
