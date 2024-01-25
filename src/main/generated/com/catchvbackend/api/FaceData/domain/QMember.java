package com.catchvbackend.api.FaceData.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1432530771L;

    public static final QMember member = new QMember("member1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<LoginStatus> loginStatus = createEnum("loginStatus", LoginStatus.class);

    public final SetPath<com.catchvbackend.api.FaceData.repository.AccountRole, EnumPath<com.catchvbackend.api.FaceData.repository.AccountRole>> roles = this.<com.catchvbackend.api.FaceData.repository.AccountRole, EnumPath<com.catchvbackend.api.FaceData.repository.AccountRole>>createSet("roles", com.catchvbackend.api.FaceData.repository.AccountRole.class, EnumPath.class, PathInits.DIRECT2);

    public final ListPath<Result, QResult> serviceResults = this.<Result, QResult>createList("serviceResults", Result.class, QResult.class, PathInits.DIRECT2);

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userPassword = createString("userPassword");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

