package com.catchvbackend.api.FaceData.domain.face;

import com.catchvbackend.api.FaceData.domain.ImageRequest;
import com.catchvbackend.api.FaceData.domain.ImageResult;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class FaceData {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "face_data_id")
    private Long id;
    @NotNull
    private String userEmail;
    @NotNull
    private String imageName;
    @NotNull
    private byte[] imageObject;
    @Min(0)
    private long imageSize;

    @ManyToOne
    private ImageResult imageResult;

    @Enumerated(EnumType.STRING)
    private FaceDataStatus status;

    @ManyToOne
    private ImageRequest imageRequest;

}
