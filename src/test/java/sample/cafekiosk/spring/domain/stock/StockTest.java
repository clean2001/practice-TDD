package sample.cafekiosk.spring.domain.stock;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class StockTest {
    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThan() {
        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // when
        boolean result = stock.isQuantityLessThan(quantity);

        // then
        assertThat(result).isTrue();

    }

    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    @Test
    void deductQuantity () {
        // given
        Stock stock = Stock.create("001", 5);
        int quantity = 3;

        // when
        stock.deductQuantity(quantity);

        // then
        assertThat(stock.getQuantity()).isEqualTo(2);
    }

    @DisplayName("주어진 개수보다 더 많은 재고를 차감하려 할 경우 예외가 발생한다.")
    @Test
    void test() {
        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // when // then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고 수량이 없습니다.");

    }
}