package com.catchvbackend.api.repository;

import com.catchvbackend.domain.ImageResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageResult, Long>, CustomImageRepository {
  List<ImageResult> findByUserEmail(String userEmail);
}