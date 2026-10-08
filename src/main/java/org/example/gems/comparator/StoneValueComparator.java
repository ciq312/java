package org.example.gems.comparator;

import org.example.gems.entity.Stone;

import java.util.Comparator;

public class StoneValueComparator implements Comparator<Stone> {
    @Override
    public int compare(Stone first, Stone second) {
        return Double.compare(first.getValue(), second.getValue());
    }
}
