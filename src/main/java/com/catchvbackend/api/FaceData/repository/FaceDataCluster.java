package com.catchvbackend.api.FaceData.repository;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of="id")
@Entity
public class FaceDataCluster {
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private FaceDataStatus status;
    @Embedded
    private FaceResource faceResource;
    @Embedded
    private ServiceResult serviceResult;
    @Embedded
    private UploadRequest uploadRequest;
}

