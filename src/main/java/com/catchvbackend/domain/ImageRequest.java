package com.catchvbackend.domain;

import com.catchvbackend.domain.face.FaceData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class ImageRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_request_id")
    private Long id;

    @Version
    private Long version;


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
    private List<FaceData> faceDatum = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_result_id")
    private ImageResult imageResult;

    @Enumerated(EnumType.STRING)
    private ResultStatus status;

    public static ImageRequest createRequest(String userEmail, String rawLen, String uploader, String setupUrl) {
        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setUserEmail(userEmail);
        imageRequest.setRawLen(rawLen);
        imageRequest.setUploader(uploader);
        imageRequest.setSetupUrl(setupUrl);
        imageRequest.setStartDate(LocalDateTime.now());
        return imageRequest;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageRequest that = (ImageRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}