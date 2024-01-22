package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.ResultStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultURLSearch {
    private String urlList;
    private String userEmail;
    private ResultStatus requestStatus;
}
