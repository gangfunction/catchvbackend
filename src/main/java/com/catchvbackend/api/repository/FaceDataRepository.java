package com.catchvbackend.api.repository;

import com.catchvbackend.domain.face.FaceData;
import java.io.IOException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface FaceDataRepository extends JpaRepository<FaceData, Long> {

  @Modifying
  @Transactional
  default void upload(Long id, MultipartFile file, String name, Long size, String userEmail) {
    try {
      byte[] fileContent = file.getBytes();
      FaceData faceData = new FaceData();
      faceData.setId(id);
      faceData.setImageObject(fileContent);
      faceData.setImageName(name);
      faceData.setImageSize(size);
      faceData.setUserEmail(userEmail);
      save(faceData);
    } catch (IOException e) {
      throw new RuntimeException("Failed to upload file", e);
    }
  }
}