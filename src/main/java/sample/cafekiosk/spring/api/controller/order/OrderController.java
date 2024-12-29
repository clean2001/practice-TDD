package sample.cafekiosk.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.service.order.OrderService;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public void createdOrder() {

    }

}