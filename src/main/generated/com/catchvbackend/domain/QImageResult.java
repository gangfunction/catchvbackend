package com.catchvbackend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImageResult is a Querydsl query type for ImageResponse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImageResult extends EntityPathBase<ImageResponse> {

    private static final long serialVersionUID = -1127990064L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImageResult imageResult = new QImageResult("imageResult");

    public final NumberPath<Integer> detectCount = createNumber("detectCount", Integer.class);

    public final StringPath detectedUrl = createString("detectedUrl");

    public final ListPath<com.catchvbackend.domain.face.FaceData, com.catchvbackend.domain.face.QFaceData> faceDatum = this.<com.catchvbackend.domain.face.FaceData, com.catchvbackend.domain.face.QFaceData>createList("faceDatum", com.catchvbackend.domain.face.FaceData.class, com.catchvbackend.domain.face.QFaceData.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath rawLen = createString("rawLen");

    public final EnumPath<ResultStatus> status = createEnum("status", ResultStatus.class);

    public final StringPath userEmail = createString("userEmail");

    public final NumberPath<Integer> videoCount = createNumber("videoCount", Integer.class);

    public QImageResult(String variable) {
        this(ImageResponse.class, forVariable(variable), INITS);
    }

    public QImageResult(Path<? extends ImageResponse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImageResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImageResult(PathMetadata metadata, PathInits inits) {
        this(ImageResponse.class, metadata, inits);
    }

    public QImageResult(Class<? extends ImageResponse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

