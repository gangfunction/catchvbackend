package com.catchvbackend.api.FaceData.domain;

import com.catchvbackend.api.FaceData.domain.face.FaceData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class ImageRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_request_id")
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
    @OneToMany(mappedBy = "imageRequest")
    private List<FaceData> faceDatum =  new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_result")
    private ImageResult ImageResult;

    @Enumerated(EnumType.STRING)
    private ResultStatus status;

    public static ImageRequest createRequest(String userEmail, String rawLen, String uploader, String setupUrl){
        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setUserEmail(userEmail);
        imageRequest.setRawLen(rawLen);
        imageRequest.setUploader(uploader);
        imageRequest.setSetupUrl(setupUrl);
        imageRequest.setStartDate(LocalDateTime.now());

        return imageRequest;
    }
}
