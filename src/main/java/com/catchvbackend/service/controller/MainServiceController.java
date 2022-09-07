package com.catchvbackend.service.controller;

import com.catchvbackend.service.SeviceRepository.Image.FaceData;
import com.catchvbackend.service.SeviceRepository.dao.FaceDataDaoJDBC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/image")
public class MainServiceController {

    @Autowired
    private FaceDataDaoJDBC imageDaoJDBC;

    @PutMapping(value = "/api")
    public void uploadImage(@RequestParam("files") List<MultipartFile> faceDatalist){
        log.info("upload");
        MultipartFile multipartFile;
        for(int i=0;i<faceDatalist.size();i++) {

            FaceData faceData = new FaceData();
            multipartFile = faceDatalist.get(i);
            try {
                faceData.setName(multipartFile.getName());
                faceData.setImage(multipartFile.getBytes());
                faceData.setSize(multipartFile.getSize());
                imageDaoJDBC.upload(faceData);
            } catch (IOException e){
                log.info("Exception"+e);
            }
        }
    }
}
//
//@PostMapping(value="/something/endpoint", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
//private ResponseEntity<?> insertSalePost(@RequestPart(value="key", required=false) DTO dto,
//                                         @RequestPart(value="file", required=true) MultipartFile file) {
//    logger.info("dto = " + dto);
//    logger.info("file = " + file);

//  ...생략

//}