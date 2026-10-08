package org.example.gems.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NecklaceTest {
    private final Stone diamond = new Diamond(1, 5000, 95, CutType.ROUND, DiamondColor.F);
    private final Stone opal = new Opal(2, 200, 35, 6, true);

    @Test
    void addsStones() {
        Necklace necklace = new Necklace("Evening");
        necklace.addStone(diamond);
        necklace.addStone(opal);

        assertEquals("Evening", necklace.getName());
        assertEquals(2, necklace.size());
        assertEquals(List.of(diamond, opal), necklace.getStones());
    }

    @Test
    void rejectsNullStone() {
        Necklace necklace = new Necklace("Evening");

        assertThrows(NullPointerException.class, () -> necklace.addStone(null));
    }

    @Test
    void returnsUnmodifiableStones() {
        Necklace necklace = new Necklace("Evening");
        necklace.addStone(diamond);

        assertThrows(UnsupportedOperationException.class, () -> necklace.getStones().add(opal));
    }

    @Test
    void replacesStonesKeepingOrder() {
        Necklace necklace = new Necklace("Evening");
        necklace.addStone(diamond);
        necklace.setStones(List.of(opal, diamond));

        assertEquals(List.of(opal, diamond), necklace.getStones());
    }

    @Test
    void comparesByNameAndStones() {
        Necklace first = new Necklace("Evening");
        first.addStone(diamond);
        Necklace same = new Necklace("Evening");
        same.addStone(diamond);
        Necklace otherName = new Necklace("Morning");
        otherName.addStone(diamond);
        Necklace otherStones = new Necklace("Evening");
        otherStones.addStone(opal);

        assertTrue(first.equals(first));
        assertEquals(first, same);
        assertEquals(first.hashCode(), same.hashCode());
        assertNotEquals(first, otherName);
        assertNotEquals(first, otherStones);
        assertFalse(first.equals(null));
        assertFalse(first.equals("Evening"));
    }

    @Test
    void describesNameAndStones() {
        Necklace necklace = new Necklace("Evening");
        necklace.addStone(opal);

        assertEquals("Necklace{name='Evening', stones=[" + opal + "]}", necklace.toString());
    }
}
