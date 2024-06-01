package com.catchvbackend.api.repository;

import com.catchvbackend.domain.ImageResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImageRepository extends JpaRepository<ImageResponse, Long> {

  List<ImageResponse> findByUserEmail(String userEmail);

  @Modifying
  @Transactional
  @Query("delete from ImageResponse i where i.userEmail = ?1")
  void deleteByUserEmail(String userEmail);

  @Modifying
  @Transactional
  default void saveResult(ImageResponse imageResponse) {
    save(imageResponse);
  }


}
