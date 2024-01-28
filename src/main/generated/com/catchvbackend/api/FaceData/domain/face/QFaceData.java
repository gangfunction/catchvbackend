package com.catchvbackend.api.FaceData.domain.face;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFaceData is a Querydsl query type for FaceData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFaceData extends EntityPathBase<FaceData> {

    private static final long serialVersionUID = 275953023L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFaceData faceData = new QFaceData("faceData");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageName = createString("imageName");

    public final ArrayPath<byte[], Byte> imageObject = createArray("imageObject", byte[].class);

    public final com.catchvbackend.api.FaceData.domain.QImageRequest imageRequest;

    public final com.catchvbackend.api.FaceData.domain.QImageResult imageResult;

    public final NumberPath<Long> imageSize = createNumber("imageSize", Long.class);

    public final EnumPath<FaceDataStatus> status = createEnum("status", FaceDataStatus.class);

    public final StringPath userEmail = createString("userEmail");

    public QFaceData(String variable) {
        this(FaceData.class, forVariable(variable), INITS);
    }

    public QFaceData(Path<? extends FaceData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFaceData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFaceData(PathMetadata metadata, PathInits inits) {
        this(FaceData.class, metadata, inits);
    }

    public QFaceData(Class<? extends FaceData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.imageRequest = inits.isInitialized("imageRequest") ? new com.catchvbackend.api.FaceData.domain.QImageRequest(forProperty("imageRequest"), inits.get("imageRequest")) : null;
        this.imageResult = inits.isInitialized("imageResult") ? new com.catchvbackend.api.FaceData.domain.QImageResult(forProperty("imageResult"), inits.get("imageResult")) : null;
    }

}

