package com.example.ex01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShippingFeeCalculatorTest {

    private ShippingFeeCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new ShippingFeeCalculator();
    }

    // 1. Cân nặng nhỏ hơn hoặc bằng 1kg, khoảng cách nhỏ hơn 10km.
    @Test
    void shouldCalculateCorrectly_WhenWeightUnder1KgAndDistanceUnder10Km() {
        // Trọng lượng 0.5kg (50.000), Khoảng cách 5km (0) => Tổng: 50.000
        double fee = calculator.calculateFee(0.5, 5.0);
        assertThat(fee).isEqualTo(50000.0);
    }

    // 2. Cân nặng lớn hơn 1kg (số nguyên), khoảng cách trong khoảng 10km đến 50km.
    @Test
    void shouldCalculateCorrectly_WhenWeightIsIntegerOver1KgAndDistanceIsBetween10And50() {
        // Trọng lượng 3.0kg: 50.000 + (2 * 10.000) = 70.000
        // Khoảng cách 20km: (20 - 10) * 5.000 = 50.000
        // => Tổng: 120.000
        double fee = calculator.calculateFee(3.0, 20.0);
        assertThat(fee).isEqualTo(120000.0);
    }

    // 3. Cân nặng là số lẻ (ví dụ: 1.5kg, 2.3kg), khoảng cách lớn hơn 50km.
    @Test
    void shouldCalculateCorrectly_WhenWeightIsFractionalAndDistanceIsOver50Km() {
        // Trọng lượng 1.5kg: 50.000 + (Math.ceil(0.5) * 10.000) = 60.000
        // Khoảng cách 55km: (40 * 5.000) + (5 * 4.000) = 200.000 + 20.000 = 220.000
        // => Tổng: 280.000
        double fee1 = calculator.calculateFee(1.5, 55.0);
        assertThat(fee1).isEqualTo(280000.0);

        // Trọng lượng 2.3kg: 50.000 + (Math.ceil(1.3) * 10.000) = 50.000 + 20.000 = 70.000
        // Khoảng cách 60km: (40 * 5.000) + (10 * 4.000) = 200.000 + 40.000 = 240.000
        // => Tổng: 310.000
        double fee2 = calculator.calculateFee(2.3, 60.0);
        assertThat(fee2).isEqualTo(310000.0);
    }

    // 4. Khoảng cách đúng 10km và đúng 50km.
    @Test
    void shouldCalculateCorrectly_AtBoundaryDistances() {
        // Trọng lượng 1kg (50.000)
        // Khoảng cách 10.0km: Không tính phí khoảng cách (0) => Tổng: 50.000
        double feeAt10Km = calculator.calculateFee(1.0, 10.0);
        assertThat(feeAt10Km).isEqualTo(50000.0);

        // Trọng lượng 1kg (50.000)
        // Khoảng cách 50.0km: (50 - 10) * 5.000 = 200.000 => Tổng: 250.000
        double feeAt50Km = calculator.calculateFee(1.0, 50.0);
        assertThat(feeAt50Km).isEqualTo(250000.0);
    }

    // 5. Kiểm tra trường hợp đầu vào không hợp lệ.
    @Test
    void shouldThrowException_WhenInputsAreInvalid() {
        // Cân nặng <= 0
        assertThatThrownBy(() -> calculator.calculateFee(0, 15))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Weight and distance must be positive");

        assertThatThrownBy(() -> calculator.calculateFee(2, -5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Weight and distance must be positive");
    }
}