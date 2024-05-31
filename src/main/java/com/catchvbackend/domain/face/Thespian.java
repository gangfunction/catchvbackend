package com.catchvbackend.domain.face;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("T")
@Getter
public class Thespian extends FaceData{
    private String actorName;
    private String actorRole;
}
