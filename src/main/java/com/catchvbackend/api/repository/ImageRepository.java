package com.catchvbackend.api.repository;

import com.catchvbackend.domain.ImageResult;
import com.catchvbackend.domain.face.FaceData;
import java.io.IOException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface ImageRepository extends JpaRepository<ImageResult, Long> {

  List<ImageResult> findByUserEmail(String userEmail);

  @Modifying
  @Transactional
  @Query("delete from ImageResult i where i.userEmail = ?1")
  void deleteByUserEmail(String userEmail);

  @Modifying
  @Transactional
  default void saveResult(ImageResult imageResult) {
    save(imageResult);
  }


}
