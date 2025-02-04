package sample.cafekiosk.spring.api.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.OrderService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService; // OrderController를 만들 때, orderService를 찾을 수가 없으므로, MockitoBean 처리를 반드시 해주어야 한다.
    @DisplayName("신규 주문을 등록할 때 상품 번호는 1개 이상이어야 한다.")
    @Test
    void createOrderWithEmptyProductNumbers() throws Exception {
        // given // 리스트가 비어서 오는 경우
        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of())
                .build();

        // when // then
        mockMvc.perform(
                post("/api/v1/orders/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품 번호는 1개 이상 있어야 합니다."))
                .andExpect(jsonPath("$.data").isEmpty());

    }

}