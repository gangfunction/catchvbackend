package com.catchvbackend.api.controller;

import com.catchvbackend.api.dto.ImageServiceDTO;
import com.catchvbackend.api.service.ImageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RequestControllerTest {

  @InjectMocks
  private RequestController requestController;

  @Mock
  private ImageService imageService;

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private RestTemplate restTemplate;

  public RequestControllerTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testUploadImage() {
    List<MultipartFile> files = List.of(); // Add mock files
    String userEmail = "test@example.com";
    String rawLen = "100";

    ImageServiceDTO serviceDTO = new ImageServiceDTO();

    when(modelMapper.map(serviceDTO, ImageServiceDTO.class)).thenReturn(serviceDTO);

    ResponseEntity<?> response = requestController.uploadImage(files, userEmail, rawLen);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testResponseCsv() {
    when(restTemplate.getForEntity("http://localhost:5001/image/toCsv", String.class)).thenReturn(ResponseEntity.ok("csv content"));

    ResponseEntity<?> response = requestController.responseCsv();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("csv content", response.getBody());
  }

}