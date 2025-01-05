package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductType.BAKERY;
import static sample.cafekiosk.spring.domain.product.ProductType.BOTTLE;

class ProductTypeTest {
    @DisplayName("HANDMADE는 재고 관련 타입이 아니다.")
    @Test
    void containsStockType1() {
        // given
        ProductType givenType = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("BOTTLE, BAKERY는 재고 관련 타입이다.")
    @Test
    void containsStockType2() {
        // given
        List<ProductType> givenTypes = List.of(BOTTLE, BAKERY);

        // when
        boolean result1 = ProductType.containsStockType(givenTypes.get(0));
        boolean result2 = ProductType.containsStockType(givenTypes.get(1));

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
    }

}