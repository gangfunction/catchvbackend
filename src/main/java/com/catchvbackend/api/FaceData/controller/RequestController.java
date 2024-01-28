package com.catchvbackend.api.FaceData.controller;

import com.catchvbackend.api.FaceData.service.ImageServiceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import static com.catchvbackend.api.FaceData.service.CustomRestTemplate.REST_TEMPLATE;

@Tag(name = "Request", description = "이미지 요청 API")
@Slf4j
@RestController
@RequestMapping(value = "/image")
@RequiredArgsConstructor
public class RequestController {

    private final ModelMapper modelMapper;

    @Operation(summary = "이미지 요청", description = "이미지를 요청합니다.")
    @PostMapping(value = "/api")
    public void uploadImage(@RequestBody ImageServiceDto serviceDto) {
        ImageServiceDto mappedDto = modelMapper.map(serviceDto, ImageServiceDto.class);
        mappedDto.uploadEvaluationLogic();
    }
    @Operation(summary = "결과리스트 응답", description = "결과 리스트를 CSV로 응답합니다.")
    @GetMapping(value = "/responseCsv")
    public void responseCsv() {
        REST_TEMPLATE.getForEntity("http://localhost:5001/image/toCsv", String.class);
    }



}
