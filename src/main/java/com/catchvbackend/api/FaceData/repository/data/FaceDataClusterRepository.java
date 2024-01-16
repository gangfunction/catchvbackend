package com.catchvbackend.api.FaceData.repository.data;

import com.catchvbackend.api.FaceData.repository.data.FaceDataCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceDataClusterRepository extends JpaRepository<FaceDataCluster, Integer> {
    
}
