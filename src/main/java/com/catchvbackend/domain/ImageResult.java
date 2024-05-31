package com.catchvbackend.domain;

import com.catchvbackend.model.Member;
import com.catchvbackend.domain.face.FaceData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter @Setter
@Entity
@NoArgsConstructor
public class ImageResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_result_id")
    private Long id;

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

    @OneToMany(mappedBy = "imageResult", cascade = CascadeType.ALL)
    private ArrayList<FaceData> faceDatum = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ResultStatus status;


    public void setMember(String userEmail){
        this.userEmail = userEmail;
        member.getServiceImageResults().add(this);
    }


    public static ImageResult createServiceResult(Integer videoCount, Integer detectCount, String userEmail, ArrayList<String> urlList) {
        ImageResult serviceImageResult = new ImageResult();
        serviceImageResult.setVideoCount(videoCount);
        serviceImageResult.setDetectCount(detectCount);
        serviceImageResult.setUserEmail(userEmail);
        serviceImageResult.setDetectedUrl(urlList.toString());

        return serviceImageResult;
    }

}
