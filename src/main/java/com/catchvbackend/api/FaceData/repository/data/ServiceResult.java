package com.catchvbackend.api.FaceData.repository.data;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Embeddable
@Builder
public class ServiceResult{
    @NotNull
    @Min(0)
    private Integer videoCount;
    @NotNull
    @Min(0)
    private Integer detectCount;
    @NotNull
    private String[] urlList;
    @NotNull
    private String userEmail;

    public ServiceResult() {

    }
}
