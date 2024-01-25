package com.catchvbackend.api.FaceData.domain;

import com.catchvbackend.api.FaceData.domain.face.FaceData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter @Setter
@Entity
@NoArgsConstructor
public class Result {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_result_id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    private FaceData faceData;

    @Enumerated(EnumType.STRING)
    private ResultStatus status;




    public void setMember(String userEmail){
        this.userEmail = userEmail;
        member.getServiceResults().add(this);
    }


    public static Result createServiceResult( Integer videoCount, Integer detectCount, String userEmail, ArrayList<String> urlList) {
        Result serviceResult = new Result();
        serviceResult.setVideoCount(videoCount);
        serviceResult.setDetectCount(detectCount);
        serviceResult.setUserEmail(userEmail);
        serviceResult.setDetectedUrl(urlList.toString());

        return serviceResult;
    }

}
