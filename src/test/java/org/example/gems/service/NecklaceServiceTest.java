package org.example.gems.service;

import org.example.gems.comparator.StoneTransparencyComparator;
import org.example.gems.comparator.StoneValueComparator;
import org.example.gems.entity.Amethyst;
import org.example.gems.entity.ColorIntensity;
import org.example.gems.entity.CutType;
import org.example.gems.entity.Diamond;
import org.example.gems.entity.DiamondColor;
import org.example.gems.entity.Necklace;
import org.example.gems.entity.Ruby;
import org.example.gems.entity.Stone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NecklaceServiceTest {
    private final Stone diamond = new Diamond(1.2, 8000, 98, CutType.ROUND, DiamondColor.E);
    private final Stone ruby = new Ruby(2, 3000, 80, CutType.OVAL, false);
    private final Stone amethyst = new Amethyst(5, 40, 60, 7, ColorIntensity.DEEP);
    private final List<Stone> candidates = List.of(amethyst, ruby, diamond);

    private Necklace necklaceOf(Stone... stones) {
        Necklace necklace = new Necklace("Test");
        for (Stone stone : stones) {
            necklace.addStone(stone);
        }
        return necklace;
    }

    @Test
    void assemblesMostValuableStonesWithinBudget() {
        Necklace necklace = new NecklaceAssembler().assemble("Evening", candidates, 9800);

        assertEquals("Evening", necklace.getName());
        assertEquals(List.of(diamond, amethyst), necklace.getStones());
    }

    @Test
    void assemblesAllStonesWhenBudgetIsEnough() {
        Necklace necklace = new NecklaceAssembler().assemble("Evening", candidates, 15800);

        assertEquals(List.of(diamond, ruby, amethyst), necklace.getStones());
    }

    @Test
    void assemblesEmptyNecklaceWithZeroBudget() {
        assertEquals(0, new NecklaceAssembler().assemble("Evening", candidates, 0).size());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, Double.NaN})
    void rejectsInvalidBudget(double budget) {
        NecklaceAssembler assembler = new NecklaceAssembler();

        assertThrows(IllegalArgumentException.class, () -> assembler.assemble("Evening", candidates, budget));
    }

    @Test
    void calculatesTotals() {
        NecklaceCalculator calculator = new NecklaceCalculator();
        Necklace necklace = necklaceOf(diamond, ruby, amethyst);

        assertEquals(8.2, calculator.calculateTotalCaratWeight(necklace), 1e-9);
        assertEquals(15800, calculator.calculateTotalValue(necklace), 1e-9);
    }

    @Test
    void calculatesZeroForEmptyNecklace() {
        NecklaceCalculator calculator = new NecklaceCalculator();
        Necklace empty = necklaceOf();

        assertEquals(0, calculator.calculateTotalCaratWeight(empty));
        assertEquals(0, calculator.calculateTotalValue(empty));
    }

    @Test
    void sortsNecklaceByComparator() {
        Necklace necklace = necklaceOf(ruby, amethyst, diamond);
        NecklaceSorter sorter = new NecklaceSorter();

        sorter.sort(necklace, new StoneValueComparator().reversed());
        assertEquals(List.of(diamond, ruby, amethyst), necklace.getStones());

        sorter.sort(necklace, new StoneTransparencyComparator());
        assertEquals(List.of(amethyst, ruby, diamond), necklace.getStones());
    }

    @Test
    void findsStonesInTransparencyRangeInclusive() {
        Necklace necklace = necklaceOf(diamond, ruby, amethyst);
        StoneFinder finder = new StoneFinder();

        assertEquals(List.of(ruby, amethyst), finder.findByTransparency(necklace, 60, 80));
        assertEquals(List.of(diamond), finder.findByTransparency(necklace, 90, 100));
        assertTrue(finder.findByTransparency(necklace, 81, 97).isEmpty());
    }

    @Test
    void rejectsInvertedTransparencyRange() {
        Necklace necklace = necklaceOf(diamond);
        StoneFinder finder = new StoneFinder();

        assertThrows(IllegalArgumentException.class, () -> finder.findByTransparency(necklace, 90, 10));
    }
}
