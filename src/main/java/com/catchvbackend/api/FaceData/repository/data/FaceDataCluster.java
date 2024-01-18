//package com.catchvbackend.api.FaceData.repository.data;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Builder @AllArgsConstructor @NoArgsConstructor
//@Getter @Setter @EqualsAndHashCode(of="id")
//@Entity
//public class FaceDataCluster {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "face_data_cluster_id")
//    private Integer id;
//
//    @Enumerated(EnumType.STRING)
//    private FaceDataStatus status;
//
//    @Embedded
//    @Column(name = "face_resource")
//    private FaceResource faceResource;
//
//    @Embedded
//    @Column(name = "service_result")
//    private ServiceResult serviceResult;
//
//    @Embedded
//    @Column(name = "upload_request")
//    private UploadRequest uploadRequest;
//}
//
