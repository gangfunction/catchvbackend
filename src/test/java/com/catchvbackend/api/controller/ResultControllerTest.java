package com.catchvbackend.api.controller;

import com.catchvbackend.api.service.ImageService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ResultControllerTest {

  @InjectMocks
  private ResultController resultController;

  @Mock
  private ImageService imageService;

  public ResultControllerTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testResultJson() {
    String requestEmail = "test@example.com";

    ResponseEntity<?> response = resultController.resultJson(requestEmail);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testResultImage() throws JSONException {
    String userEmail = "test@example.com";
    List results = List.of(); // Add mock results

    when(imageService.checkResult(userEmail)).thenReturn(results);

    ResponseEntity<?> response = resultController.resultImage(userEmail);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(results, response.getBody());
  }

}