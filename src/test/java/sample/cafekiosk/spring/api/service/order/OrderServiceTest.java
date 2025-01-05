package sample.cafekiosk.spring.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.controller.order.request.OrderResponse;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.orderProduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;
import sample.cafekiosk.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.*;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void tearDown() {
//        productRepository.deleteAll();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderProductRepository.deleteAllInBatch();
    }

    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrder() {
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .build();

        Product product3 = Product.builder()
                .productNumber("003")
                .type(HANDMADE)
                .sellingStatus(STOP_SELLING)
                .name("팥빙수")
                .price(4500)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();

        LocalDateTime registeredDate = LocalDateTime.now();
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest, registeredDate);

        // then
        assertThat(orderResponse.getId()).isNotNull(); // 아이디가 null이 아님
        assertThat(orderResponse).extracting("registeredDateTime", "totalPrice")
                .contains(registeredDate, 8500);
        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 4000),
                        tuple("002", 4500)
                );
    }

    @DisplayName("주문 시에 주문 시간을 기록한다.")
    @Test
    void registeredDateTime() {
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .build();

        Product product3 = Product.builder()
                .productNumber("003")
                .type(HANDMADE)
                .sellingStatus(STOP_SELLING)
                .name("팥빙수")
                .price(4500)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();

        LocalDateTime registeredDate = LocalDateTime.now();
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest, registeredDate);

        // then
        assertThat(orderResponse.getRegisteredDateTime()).isEqualTo(registeredDate);
    }

    @DisplayName("같은 상품을 여러 개 주문할 수 있다.")
    @Test
    void orderDuplicateProducts() {
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .build();

        Product product3 = Product.builder()
                .productNumber("003")
                .type(HANDMADE)
                .sellingStatus(STOP_SELLING)
                .name("팥빙수")
                .price(4500)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001")) // 아메리카노 2개
                .build();

        LocalDateTime registeredDate = LocalDateTime.now();
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest, registeredDate);

        // then
        assertThat(orderResponse.getTotalPrice()).isEqualTo(8000);
    }

    @DisplayName("상품 주문 개수만큼 재고가 차감된다.")
    @Test
    void createOrderWithStock() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        Product product1 = createProduct(BOTTLE, "001", 1000);
        Product product2 = createProduct(BAKERY, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 2);
        Stock stock3 = Stock.create("003", 3);
        stockRepository.saveAll(List.of(stock1, stock2, stock3));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "002", "003")) // 2000 + 3000 + 5000 = 10000
                .build();

        // when
        OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 10000);
        assertThat(orderResponse.getProducts()).hasSize(3)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("001", 1000),
                        tuple("002", 3000),
                        tuple("003", 5000)
                );

        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(3)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                tuple("001", 0),
                tuple("002", 1),
                tuple("003", 2)
        );
    }


    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price).build();
    }

}