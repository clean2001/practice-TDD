package sample.cafekiosk.spring.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 부모 클래스를 테이블로 매핑 시킬 필요 없이 **컬럼 정보만 매핑 정보로 필요한 경우** 다는 어노테이션
@EntityListeners(AuditingEntityListener.class) // SpringBootApplication에도 Enable 어노테이션 달아줘야한다.
public abstract class BaseEntity {
    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;
}
