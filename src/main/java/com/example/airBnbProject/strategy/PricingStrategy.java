package com.example.airBnbProject.strategy;

import com.example.airBnbProject.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
