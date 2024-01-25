package com.catchvbackend.api.FaceData.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResult is a Querydsl query type for Result
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QResult extends EntityPathBase<Result> {

    private static final long serialVersionUID = 1575873750L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResult result = new QResult("result");

    public final NumberPath<Integer> detectCount = createNumber("detectCount", Integer.class);

    public final StringPath detectedUrl = createString("detectedUrl");

    public final com.catchvbackend.api.FaceData.domain.face.QFaceData faceData;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath rawLen = createString("rawLen");

    public final EnumPath<ResultStatus> status = createEnum("status", ResultStatus.class);

    public final StringPath userEmail = createString("userEmail");

    public final NumberPath<Integer> videoCount = createNumber("videoCount", Integer.class);

    public QResult(String variable) {
        this(Result.class, forVariable(variable), INITS);
    }

    public QResult(Path<? extends Result> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResult(PathMetadata metadata, PathInits inits) {
        this(Result.class, metadata, inits);
    }

    public QResult(Class<? extends Result> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.faceData = inits.isInitialized("faceData") ? new com.catchvbackend.api.FaceData.domain.face.QFaceData(forProperty("faceData"), inits.get("faceData")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

