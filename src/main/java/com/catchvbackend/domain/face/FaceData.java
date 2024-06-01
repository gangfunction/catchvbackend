package com.catchvbackend.domain.face;

import com.catchvbackend.domain.ImageRequest;
import com.catchvbackend.domain.ImageResult;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public class FaceData {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "face_data_id")
    private Long id;
    @NotNull
    private String userEmail;
    @NotNull
    private String imageName;
    @NotNull
    @Lob
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
