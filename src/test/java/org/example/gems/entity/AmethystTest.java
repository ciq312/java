package org.example.gems.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AmethystTest {
    private final Amethyst amethyst = new Amethyst(5, 40, 85, 7, ColorIntensity.DEEP);

    @Test
    void returnsProperties() {
        assertEquals(StoneType.AMETHYST, amethyst.getType());
        assertEquals(7, amethyst.getHardness());
        assertEquals(ColorIntensity.DEEP, amethyst.getIntensity());
    }

    @Test
    void equalsStoneWithSameProperties() {
        Amethyst same = new Amethyst(5, 40, 85, 7, ColorIntensity.DEEP);

        assertEquals(amethyst, same);
        assertEquals(amethyst.hashCode(), same.hashCode());
    }

    @Test
    void notEqualsWhenPropertyDiffers() {
        assertNotEquals(amethyst, new Amethyst(6, 40, 85, 7, ColorIntensity.DEEP));
        assertNotEquals(amethyst, new Amethyst(5, 40, 85, 6.5, ColorIntensity.DEEP));
        assertNotEquals(amethyst, new Amethyst(5, 40, 85, 7, ColorIntensity.LIGHT));
    }

    @Test
    void describesAllProperties() {
        assertEquals("AMETHYST{carat=5.0, pricePerCarat=40.0, transparency=85.0, hardness=7.0, intensity=DEEP}",
                amethyst.toString());
    }
}
