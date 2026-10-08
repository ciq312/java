package org.example.gems.comparator;

import org.example.gems.entity.Stone;

import java.util.Comparator;

public class StoneCaratWeightComparator implements Comparator<Stone> {
    @Override
    public int compare(Stone first, Stone second) {
        return Double.compare(first.getCaratWeight(), second.getCaratWeight());
    }
}
