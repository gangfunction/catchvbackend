package com.catchvbackend.domain.face;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QThespian is a Querydsl query type for Thespian
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QThespian extends EntityPathBase<Thespian> {

    private static final long serialVersionUID = 36022065L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QThespian thespian = new QThespian("thespian");

    public final QFaceData _super;

    public final StringPath actorName = createString("actorName");

    public final StringPath actorRole = createString("actorRole");

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath imageName;

    //inherited
    public final ArrayPath<byte[], Byte> imageObject;

    // inherited
    public final com.catchvbackend.domain.QImageRequest imageRequest;

    // inherited
    public final com.catchvbackend.domain.QImageResult imageResult;

    //inherited
    public final NumberPath<Long> imageSize;

    //inherited
    public final EnumPath<FaceDataStatus> status;

    //inherited
    public final StringPath userEmail;

    public QThespian(String variable) {
        this(Thespian.class, forVariable(variable), INITS);
    }

    public QThespian(Path<? extends Thespian> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QThespian(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QThespian(PathMetadata metadata, PathInits inits) {
        this(Thespian.class, metadata, inits);
    }

    public QThespian(Class<? extends Thespian> type, PathMetadata metadata, PathInits inits) {
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

