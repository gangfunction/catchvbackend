package com.catchvbackend.api.FaceData.repository.data;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Embeddable
public class UploadRequest{

    @NotNull
    private String rawLen;
    @NotNull
    private List<MultipartFile> files;
    @NotNull
    private String startDate;
    @NotNull
    private String uploader;
    @NotNull
    private String userEmail;

}
