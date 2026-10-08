package org.example.gems.service;

import org.example.gems.entity.Necklace;
import org.example.gems.entity.Stone;

public class NecklaceCalculator {
    public double calculateTotalCaratWeight(Necklace necklace) {
        return necklace.getStones().stream()
                .mapToDouble(Stone::getCaratWeight)
                .sum();
    }

    public double calculateTotalValue(Necklace necklace) {
        return necklace.getStones().stream()
                .mapToDouble(Stone::getValue)
                .sum();
    }
}
