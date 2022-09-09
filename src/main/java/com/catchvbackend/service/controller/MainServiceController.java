package com.catchvbackend.service.controller;

import com.catchvbackend.service.SeviceRepository.Image.FaceData;
import com.catchvbackend.service.SeviceRepository.dao.FaceDataDaoJDBC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
<<<<<<< Updated upstream
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
>>>>>>> Stashed changes
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/image")
public class MainServiceController {

    @Autowired
    private FaceDataDaoJDBC imageDaoJDBC;

    @PostMapping(value = "/api")
    public void uploadImage(@RequestParam("files") List<MultipartFile> faceDatalist,
                            @RequestParam("email") String userEmail){
        log.info("upload");
        log.info(""+userEmail);
        MultipartFile multipartFile;
        for(int i=0;i<faceDatalist.size();i++) {
            if(i==faceDatalist.size()-1){
                log.info(""+faceDatalist.get(i));
            }else {
                FaceData faceData = new FaceData();
                multipartFile = faceDatalist.get(i);
                try {
                    faceData.setName(multipartFile.getName());
                    faceData.setImage(multipartFile.getBytes());
                    faceData.setSize(multipartFile.getSize());
                    imageDaoJDBC.upload(faceData);
                } catch (IOException e) {
                    log.info("Exception" + e);
                }
            }
        }
    }

    @PostMapping(value = "/api/flask")
    public void flaskApi(@RequestParam("files") List<MultipartFile> faceDatalist,
                                 @RequestParam("email") String userEmail) {

<<<<<<< Updated upstream
        String url = "http://localhost:5001/user/"+userEmail;
=======
        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    map.add("files",  file.getResource());
                }else{
                    log.info("No files found");
                }
            }
            map.add("userEmail", userEmail);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
>>>>>>> Stashed changes

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

<<<<<<< Updated upstream
        MultiValueMap<String, List<byte[]>> params = new LinkedMultiValueMap<>();

        ArrayList<byte[]> list = new ArrayList<>();

        for(int i=0;i<faceDatalist.size();i++) {
=======
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
    public void receiveMessage(@RequestBody String something){
        log.info("Received message");
    }

    @PostMapping(value = "/api")
    public void uploadImage(@RequestParam("files") List<MultipartFile> faceDatalist,
                            @RequestParam("email") String userEmail){
        log.info("uploaded by"+userEmail);
        //대기열 존재하지 않을시
        send(faceDatalist, userEmail);


        //만약 대기열이 존재할시
        MultipartFile multipartFile ;
        for (MultipartFile file  : faceDatalist) {
            FaceData faceData = new FaceData();
            multipartFile = file;
>>>>>>> Stashed changes
            try {
                list.add(faceDatalist.get(i).getBytes());
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        params.add("files", list);

        HttpEntity<MultiValueMap<String, List<byte[]>>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK){
            log.info("connect"+response);
        }
    }
}
