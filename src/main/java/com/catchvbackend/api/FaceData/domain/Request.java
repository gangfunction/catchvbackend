package com.catchvbackend.api.FaceData.domain;

import com.catchvbackend.api.FaceData.domain.face.FaceData;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Request{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_request_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @NotNull
    private String rawLen;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private String uploader;
    @NotNull
    private String userEmail;
    @NotNull
    private String setupUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "uploadRequest")
    private List<FaceData> faceDatum =  new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_result_id")
    private Result serviceResult;

    @Enumerated(EnumType.STRING)
    private ResultStatus status;


    public static Request createUploadRequest(String rawLen, LocalDateTime startDate, String uploader, String userEmail, ResultStatus status, FaceData... faceDatas) {
        Request uploadRequest = new Request();
        uploadRequest.setRawLen(rawLen);
        uploadRequest.setUploader(uploader);
        uploadRequest.setUserEmail(userEmail);
        uploadRequest.setStatus(status);
        for (FaceData faceDatum : faceDatas) {
            uploadRequest.addFaceData(faceDatum);
        }
        uploadRequest.setStartDate(LocalDateTime.now());

        return uploadRequest;
    }

    private void addFaceData(FaceData faceData) {
        faceDatum.add(faceData);
        faceData.setUploadRequest(this);
    }
}
