package com.catchvbackend.api.repository;

import com.catchvbackend.domain.ImageResult;

public interface CustomImageRepository {
  void saveResult(ImageResult imageResult);
  void upload(Long id, byte[] list, String name, Long size, String userEmail);
}
