package com.catchvbackend.api.FaceData.repository.data;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of="id")
@Entity
public class FaceDataCluster {
    @Id
    @NotEmpty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

