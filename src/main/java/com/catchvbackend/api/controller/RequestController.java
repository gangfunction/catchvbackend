package com.catchvbackend.api.controller;

import com.catchvbackend.api.dto.ImageServiceDTO;
import com.catchvbackend.api.service.ImageService;
import com.catchvbackend.api.service.JsonProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "Request", description = "이미지 요청 API")
@Slf4j
@RestController
@RequestMapping(value = "/image")
@RequiredArgsConstructor
public class RequestController {

  private final ModelMapper modelMapper;
  private final ImageService imageService;
  private final RestTemplate restTemplate;
  private final JsonProcessingService jsonProcessingService;

  @Operation(summary = "이미지 요청", description = "이미지를 요청합니다.")
  @PostMapping(value = "/api", consumes = "multipart/form-data")
  public ResponseEntity<?> uploadImage(@RequestParam("files") List<MultipartFile> files,
      @RequestParam("userEmail") String userEmail,
      @RequestParam("rawLen") String rawLen) {
    ImageServiceDTO serviceDto = ImageServiceDTO.builder()
        .files(files)
        .userEmail(userEmail)
        .rawLen(rawLen)
        .build();
    ImageServiceDTO mappedDto = modelMapper.map(serviceDto, ImageServiceDTO.class);
    imageService.uploadEvaluationLogic(mappedDto);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "결과리스트 응답", description = "결과 리스트를 CSV로 응답합니다.")
  @GetMapping(value = "/responseCsv")
  public ResponseEntity<?> responseCsv() {
    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:5001/image/toCsv",
        String.class);
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=results.csv");
    headers.set(HttpHeaders.CONTENT_TYPE, "application/csv");
    return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
  }
  @PostMapping(value = "/processImageJson", consumes = "application/json")
  public ResponseEntity<String> processImageJson(@RequestBody String jsonPayload) {
    jsonProcessingService.processJsonPayload(jsonPayload);
    return ResponseEntity.ok("Image JSON processed successfully");
  }
}



