package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

class OrderTest {
    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        // given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when
        LocalDateTime registeredDateTime = LocalDateTime.now();
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .type(HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }

    @DisplayName("주문 생성 시 초기 상태는 init이다.")
    @Test
    void init() {
        // given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when
        LocalDateTime registeredDateTime = LocalDateTime.now();
        Order order = Order.create(products, registeredDateTime);

        // then
        // isEqualByComparingTo: enum 값을 그 자체로 비교해주는 메서드
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }


    @DisplayName("주문 생성 시 시간을 기록한다.")
    @Test
    void registerTime() {
        // given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when
        LocalDateTime registeredDateTime = LocalDateTime.now();
        Order order = Order.create(products, registeredDateTime);

        // then
        // isEqualByComparingTo: enum 값을 그 자체로 비교해주는 메서드
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

}