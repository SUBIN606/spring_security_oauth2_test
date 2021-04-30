package test.oauth2.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -906922290L;

    public static final QMember member = new QMember("member1");

    public final StringPath api_pwd = createString("api_pwd");

    public final StringPath biz_manager = createString("biz_manager");

    public final StringPath bizid = createString("bizid");

    public final StringPath bizpwd = createString("bizpwd");

    public final StringPath level = createString("level");

    public final StringPath name = createString("name");

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

