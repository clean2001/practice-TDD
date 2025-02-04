package sample.cafekiosk.spring.api.controller.order.request;

import jakarta.validation.constraints.Size;
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
    @Size(min = 1, message = "상품 번호는 1개 이상 있어야 합니다.")
    private List<String> productNumbers;
}
