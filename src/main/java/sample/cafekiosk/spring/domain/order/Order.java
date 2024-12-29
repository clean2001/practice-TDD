package sample.cafekiosk.spring.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "orders") // order 키워드 때문에 orders로 테이즐 명을 따로 지정
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredDateTime;
}
