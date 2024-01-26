package com.catchvbackend.api.FaceData.domain;

import com.catchvbackend.api.FaceData.domain.face.FaceData;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
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

    public static Request  createRequest(String userEmail, String rawLen, String uploader, String setupUrl){
        Request request = new Request();
        request.setUserEmail(userEmail);
        request.setRawLen(rawLen);
        request.setUploader(uploader);
        request.setSetupUrl(setupUrl);
        request.setStartDate(LocalDateTime.now());

        return request;
    }
}
