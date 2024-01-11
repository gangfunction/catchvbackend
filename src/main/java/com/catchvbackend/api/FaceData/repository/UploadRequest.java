package com.catchvbackend.api.FaceData.repository;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Embeddable;
import java.util.List;

@Getter
@Embeddable
public class UploadRequest{
    private String rawLen;
    private List<MultipartFile> files;
    private String startDate;
    private String uploader;
    private String userEmail;

}
