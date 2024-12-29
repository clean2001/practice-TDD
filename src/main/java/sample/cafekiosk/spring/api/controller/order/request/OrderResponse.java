package sample.cafekiosk.spring.api.controller.order.request;

import lombok.Getter;
import sample.cafekiosk.spring.api.controller.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {
    private Long id;
    private int totalPrice;
    private LocalDateTime registerDateTime;
    private List<ProductResponse> products;
}
