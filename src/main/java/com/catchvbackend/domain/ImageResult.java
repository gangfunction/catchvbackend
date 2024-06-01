package com.catchvbackend.domain;

import com.catchvbackend.domain.face.FaceData;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ImageResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_result_id")
    private Long id;

    @Version
    private Long version;

    @NotNull
    @Min(0)
    private Integer videoCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Nullable
    @Min(0)
    private Integer detectCount;

    @Nullable
    private String detectedUrl;

    @NotNull
    private String userEmail;

    @Nullable
    private String rawLen;

    @OneToMany(mappedBy = "imageResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FaceData> faceDatum = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ResultStatus status;

    public void setMember(Member member) {
        this.member = member;
        member.getServiceImageResults().add(this);
    }

    public void addFaceData(FaceData faceData) {
        faceDatum.add(faceData);
        faceData.setImageResult(this);
    }

    public void removeFaceData(FaceData faceData) {
        faceDatum.remove(faceData);
        faceData.setImageResult(null);
    }

    public static ImageResult createServiceResult(Integer videoCount, Integer detectCount,
        String userEmail, List<String> urlList) {
        ImageResult serviceImageResult = new ImageResult();
        serviceImageResult.setVideoCount(videoCount);
        serviceImageResult.setDetectCount(detectCount);
        serviceImageResult.setUserEmail(userEmail);
        serviceImageResult.setDetectedUrl(String.join(",", urlList));
        return serviceImageResult;
    }

    public List<FaceData> getFaceDatum() {
        return Collections.unmodifiableList(faceDatum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ImageResult that = (ImageResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
