package com.catchvbackend.api.controller;

import com.catchvbackend.api.service.ImageService;
import com.catchvbackend.domain.ImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Result", description = "이미지 처리 결과 API")
@Slf4j
@RestController
@RequestMapping(value = "/image")
@RequiredArgsConstructor
public class ResponseController {
    private final ImageService imageService;


    @Operation(summary = "이미지 처리 결과를 Json으로 매핑합니다.", description = "이미지 처리 결과를 Json으로 매핑합니다.")
    @PutMapping(value = "/result")
    public ResponseEntity<?> resultJson(@RequestBody String requestEmail) {
        try {
            imageService.resultJsonProcessing(requestEmail);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error processing result JSON", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing result JSON");
        }
    }
    @Operation(summary = "이미지 처리 결과", description = "이미지 처리 결과를 응답합니다.")
    @GetMapping(value = "/result")
    public ResponseEntity<?> resultImage(@RequestParam String userEmail) {
        try {
            List<ImageResponse> results = imageService.checkResult(userEmail);
            if (results.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No results found for the given email");
            }
            return ResponseEntity.ok(results);
        } catch (JSONException e) {
            log.error("Error fetching results", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching results");
        }
    }

}
