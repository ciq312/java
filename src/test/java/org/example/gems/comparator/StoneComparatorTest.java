package org.example.gems.comparator;

import org.example.gems.entity.Opal;
import org.example.gems.entity.Stone;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StoneComparatorTest {
    private final Stone heavy = new Opal(5, 100, 20, 6, false);
    private final Stone clear = new Opal(1, 300, 90, 6, false);
    private final Stone cheap = new Opal(2, 50, 50, 6, false);

    private List<Stone> stones() {
        return new ArrayList<>(List.of(heavy, clear, cheap));
    }

    @Test
    void sortsByValue() {
        List<Stone> stones = stones();
        stones.sort(new StoneValueComparator());

        assertEquals(List.of(cheap, clear, heavy), stones);
    }

    @Test
    void sortsByCaratWeight() {
        List<Stone> stones = stones();
        stones.sort(new StoneCaratWeightComparator());

        assertEquals(List.of(clear, cheap, heavy), stones);
    }

    @Test
    void sortsByTransparency() {
        List<Stone> stones = stones();
        stones.sort(new StoneTransparencyComparator());

        assertEquals(List.of(heavy, cheap, clear), stones);
    }
}
