package com.catchvbackend.api.FaceData.controller;

import com.catchvbackend.api.FaceData.domain.ImageResult;
import com.catchvbackend.api.FaceData.service.ImageServiceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Result", description = "이미지 처리 결과 API")
@Slf4j
@RestController
@RequestMapping(value = "/image")
@RequiredArgsConstructor
public class ResultController {
    private ImageServiceDto serviceDto;

    public ResultController(ImageServiceDto serviceDto) {
        this.serviceDto = serviceDto;
    }

    @Operation(summary = "이미지 처리 결과를 Json으로 매핑합니다.", description = "이미지 처리 결과를 Json으로 매핑합니다.")
    @PutMapping(value = "/result")
    public void resultJson(@RequestBody String requestEmail) {
        serviceDto.resultJsonProcessing(requestEmail);
    }
    @Operation(summary = "이미지 처리 결과", description = "이미지 처리 결과를 응답합니다.")
    @GetMapping(value = "/result")
    public List<ImageResult> resultImage(@RequestBody String userEmail) throws JSONException {
        return serviceDto.checkResult(userEmail);
    }

}
