package com.catchvbackend.api.FaceData.domain.face;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.catchvbackend.domain.face.Civis;
import com.catchvbackend.domain.face.FaceDataStatus;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCivis is a Querydsl query type for Civis
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCivis extends EntityPathBase<Civis> {

    private static final long serialVersionUID = 885438242L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCivis civis = new QCivis("civis");

    public final QFaceData _super;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath imageName;

    //inherited
    public final ArrayPath<byte[], Byte> imageObject;

    // inherited
    public final com.catchvbackend.api.FaceData.domain.QImageRequest imageRequest;

    // inherited
    public final com.catchvbackend.api.FaceData.domain.QImageResult imageResult;

    //inherited
    public final NumberPath<Long> imageSize;

    //inherited
    public final EnumPath<FaceDataStatus> status;

    public final StringPath userAge = createString("userAge");

    //inherited
    public final StringPath userEmail;

    public final StringPath userGender = createString("userGender");

    public final StringPath userName = createString("userName");

    public QCivis(String variable) {
        this(Civis.class, forVariable(variable), INITS);
    }

    public QCivis(Path<? extends Civis> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCivis(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCivis(PathMetadata metadata, PathInits inits) {
        this(Civis.class, metadata, inits);
    }

    public QCivis(Class<? extends Civis> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QFaceData(type, metadata, inits);
        this.id = _super.id;
        this.imageName = _super.imageName;
        this.imageObject = _super.imageObject;
        this.imageRequest = _super.imageRequest;
        this.imageResult = _super.imageResult;
        this.imageSize = _super.imageSize;
        this.status = _super.status;
        this.userEmail = _super.userEmail;
    }

}

