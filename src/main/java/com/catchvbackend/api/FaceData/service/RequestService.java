package com.catchvbackend.api.FaceData.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RequestService {

    static void faceDataRequestModelMapping(FaceDataRequestModel serviceRequestData, LinkedMultiValueMap<String, Object> map) {
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

}
