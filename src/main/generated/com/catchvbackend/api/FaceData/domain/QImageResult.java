package com.catchvbackend.api.FaceData.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.catchvbackend.domain.ImageResult;
import com.catchvbackend.domain.ResultStatus;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImageResult is a Querydsl query type for ImageResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImageResult extends EntityPathBase<ImageResult> {

    private static final long serialVersionUID = 396923967L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImageResult imageResult = new QImageResult("imageResult");

    public final NumberPath<Integer> detectCount = createNumber("detectCount", Integer.class);

    public final StringPath detectedUrl = createString("detectedUrl");

    public final com.catchvbackend.api.FaceData.domain.face.QFaceData faceData;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath rawLen = createString("rawLen");

    public final EnumPath<ResultStatus> status = createEnum("status", ResultStatus.class);

    public final StringPath userEmail = createString("userEmail");

    public final NumberPath<Integer> videoCount = createNumber("videoCount", Integer.class);

    public QImageResult(String variable) {
        this(ImageResult.class, forVariable(variable), INITS);
    }

    public QImageResult(Path<? extends ImageResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImageResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImageResult(PathMetadata metadata, PathInits inits) {
        this(ImageResult.class, metadata, inits);
    }

    public QImageResult(Class<? extends ImageResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.faceData = inits.isInitialized("faceData") ? new com.catchvbackend.api.FaceData.domain.face.QFaceData(forProperty("faceData"), inits.get("faceData")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

