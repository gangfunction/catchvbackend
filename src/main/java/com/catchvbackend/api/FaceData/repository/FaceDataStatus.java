package com.catchvbackend.api.FaceData.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public enum FaceDataStatus {
    COMMIT,ROLLBACK,TRANSFER,COMPLETED
}
