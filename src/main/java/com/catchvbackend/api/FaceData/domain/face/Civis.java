package com.catchvbackend.api.FaceData.domain.face;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("C")
@Getter
public class Civis extends FaceData{
    private String userName;
    private String userGender;
    private String userAge;

}
