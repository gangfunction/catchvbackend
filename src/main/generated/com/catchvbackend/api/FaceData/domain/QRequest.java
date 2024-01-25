package com.catchvbackend.api.FaceData.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRequest is a Querydsl query type for Request
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequest extends EntityPathBase<Request> {

    private static final long serialVersionUID = 1605592310L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRequest request = new QRequest("request");

    public final ListPath<com.catchvbackend.api.FaceData.domain.face.FaceData, com.catchvbackend.api.FaceData.domain.face.QFaceData> faceDatum = this.<com.catchvbackend.api.FaceData.domain.face.FaceData, com.catchvbackend.api.FaceData.domain.face.QFaceData>createList("faceDatum", com.catchvbackend.api.FaceData.domain.face.FaceData.class, com.catchvbackend.api.FaceData.domain.face.QFaceData.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath rawLen = createString("rawLen");

    public final QResult serviceResult;

    public final StringPath setupUrl = createString("setupUrl");

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final EnumPath<ResultStatus> status = createEnum("status", ResultStatus.class);

    public final StringPath uploader = createString("uploader");

    public final StringPath userEmail = createString("userEmail");

    public QRequest(String variable) {
        this(Request.class, forVariable(variable), INITS);
    }

    public QRequest(Path<? extends Request> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRequest(PathMetadata metadata, PathInits inits) {
        this(Request.class, metadata, inits);
    }

    public QRequest(Class<? extends Request> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.serviceResult = inits.isInitialized("serviceResult") ? new QResult(forProperty("serviceResult"), inits.get("serviceResult")) : null;
    }

}

