package sample.cafekiosk.spring.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber; // 001, 002, ..

    private String name;

    private int price;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    private ProductSellingType sellingStatus;

    @Builder
    public Product(String productNumber, String name, int price, ProductType type, ProductSellingType sellingStatus) {
        this.productNumber = productNumber;
        this.name = name;
        this.price = price;
        this.type = type;
        this.sellingStatus = sellingStatus;
    }
}
