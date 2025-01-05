package sample.cafekiosk.spring.api.controller.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.api.controller.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class OrderResponse {
    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products;

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .registeredDateTime(order.getRegisteredDateTime())
                .products(order.getOrderProducts().stream()
                        .map(op -> ProductResponse.of(op.getProduct()))
                        .toList())
                .build();
    }
}
