package com.catchvbackend.api.FaceData.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;

public enum FaceDataStatus {
    COMMIT,ROLLBACK,TRANSFER,COMPLETED
}
