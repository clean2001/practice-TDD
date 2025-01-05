package sample.cafekiosk.spring.api.controller.order;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.controller.order.request.OrderResponse;
import sample.cafekiosk.spring.api.service.order.OrderService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public OrderResponse createdOrder(@RequestBody OrderCreateRequest request) {
        LocalDateTime registeredTime = LocalDateTime.now();
        return orderService.createOrder(request, registeredTime);
    }

}
