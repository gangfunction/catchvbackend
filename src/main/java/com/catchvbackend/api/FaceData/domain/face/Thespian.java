package com.catchvbackend.api.FaceData.domain.face;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("T")
@Getter
public class Thespian extends FaceData{
    private String actorName;
    private String actorRole;
}
