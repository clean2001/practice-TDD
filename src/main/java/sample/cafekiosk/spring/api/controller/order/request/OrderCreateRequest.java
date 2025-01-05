package sample.cafekiosk.spring.api.controller.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderCreateRequest {
    private List<String> productNumbers;
}
