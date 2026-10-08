package org.example.gems.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EmeraldTest {
    private final Emerald emerald = new Emerald(1.5, 2500, 70, CutType.EMERALD, "Colombia");

    @Test
    void returnsProperties() {
        assertEquals(StoneType.EMERALD, emerald.getType());
        assertEquals("Colombia", emerald.getOrigin());
    }

    @Test
    void equalsStoneWithSameProperties() {
        Emerald same = new Emerald(1.5, 2500, 70, CutType.EMERALD, "Colombia");

        assertEquals(emerald, same);
        assertEquals(emerald.hashCode(), same.hashCode());
    }

    @Test
    void notEqualsWhenPropertyDiffers() {
        assertNotEquals(emerald, new Emerald(1.5, 2500, 70, CutType.PEAR, "Colombia"));
        assertNotEquals(emerald, new Emerald(1.5, 2500, 70, CutType.EMERALD, "Zambia"));
    }

    @Test
    void describesAllProperties() {
        assertEquals("EMERALD{carat=1.5, pricePerCarat=2500.0, transparency=70.0, cut=EMERALD, origin=Colombia}",
                emerald.toString());
    }
}
