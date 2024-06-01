package com.catchvbackend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImageRequest is a Querydsl query type for ImageRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImageRequest extends EntityPathBase<ImageRequest> {

    private static final long serialVersionUID = -609807300L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImageRequest imageRequest = new QImageRequest("imageRequest");

    public final ListPath<com.catchvbackend.domain.face.FaceData, com.catchvbackend.domain.face.QFaceData> faceDatum = this.<com.catchvbackend.domain.face.FaceData, com.catchvbackend.domain.face.QFaceData>createList("faceDatum", com.catchvbackend.domain.face.FaceData.class, com.catchvbackend.domain.face.QFaceData.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QImageResult imageResult;

    public final QMember member;

    public final StringPath rawLen = createString("rawLen");

    public final StringPath setupUrl = createString("setupUrl");

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final EnumPath<ResultStatus> status = createEnum("status", ResultStatus.class);

    public final StringPath uploader = createString("uploader");

    public final StringPath userEmail = createString("userEmail");

    public QImageRequest(String variable) {
        this(ImageRequest.class, forVariable(variable), INITS);
    }

    public QImageRequest(Path<? extends ImageRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImageRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImageRequest(PathMetadata metadata, PathInits inits) {
        this(ImageRequest.class, metadata, inits);
    }

    public QImageRequest(Class<? extends ImageRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.imageResult = inits.isInitialized("imageResult") ? new QImageResult(forProperty("imageResult"), inits.get("imageResult")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

