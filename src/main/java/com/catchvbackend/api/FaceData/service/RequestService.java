package com.catchvbackend.api.FaceData.service;

import com.catchvbackend.api.FaceData.domain.Request;
import com.catchvbackend.api.FaceData.domain.face.FaceData;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@NoArgsConstructor
public class RequestService {
    private FaceDataServiceDto serviceRequestData;

     public void faceDataRequestModelMapping(List<MultipartFile> files, LinkedMultiValueMap<String, Object> map) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()){
                map.add("files",  file.getResource());
                map.add("userEmail", serviceRequestData.getUserEmail());
                map.add("startDate", serviceRequestData.getStartDate());
                map.add("raw_len" , serviceRequestData.getRawLen());
            }else{
                log.info("No files found");
            }
        }
    }

}
