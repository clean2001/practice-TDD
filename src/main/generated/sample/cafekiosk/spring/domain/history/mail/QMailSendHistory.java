package sample.cafekiosk.spring.domain.history.mail;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMailSendHistory is a Querydsl query type for MailSendHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMailSendHistory extends EntityPathBase<MailSendHistory> {

    private static final long serialVersionUID = -448139897L;

    public static final QMailSendHistory mailSendHistory = new QMailSendHistory("mailSendHistory");

    public final sample.cafekiosk.spring.domain.QBaseEntity _super = new sample.cafekiosk.spring.domain.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    public final StringPath fromEmail = createString("fromEmail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = _super.modifiedDateTime;

    public final StringPath subject = createString("subject");

    public final StringPath toEmail = createString("toEmail");

    public QMailSendHistory(String variable) {
        super(MailSendHistory.class, forVariable(variable));
    }

    public QMailSendHistory(Path<? extends MailSendHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMailSendHistory(PathMetadata metadata) {
        super(MailSendHistory.class, metadata);
    }

}

