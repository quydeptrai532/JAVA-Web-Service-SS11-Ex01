package com.example.ex01;

public class ShippingFeeCalculator {

    public double calculateFee(double weightKg, double distanceKm) {
        if (weightKg <= 0 || distanceKm <= 0) {
            throw new IllegalArgumentException("Weight and distance must be positive");
        }

        // 1. Tính phí cân nặng (Sửa Math.floor thành Math.ceil)
        double weightFee = 50000;
        if (weightKg > 1) {
            weightFee += Math.ceil(weightKg - 1) * 10000;
        }

        // 2. Tính phí khoảng cách (Sửa thành tính phí lũy tiến)
        double distanceFee = 0;
        if (distanceKm > 10 && distanceKm <= 50) {
            // Từ trên 10km đến 50km
            distanceFee = (distanceKm - 10) * 5000;
        } else if (distanceKm > 50) {
            // Trên 50km: (40km ở mức giá 5k) + (phần vượt quá 50km ở mức giá 4k)
            distanceFee = (40 * 5000) + ((distanceKm - 50) * 4000);
        }
        // Nhánh ngầm định khoảng cách <= 10km thì distanceFee = 0

        return weightFee + distanceFee;
    }
}