package org.example.gems.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TopazTest {
    private final Topaz topaz = new Topaz(4, 60, 90, 8, TopazColor.BLUE);

    @Test
    void returnsProperties() {
        assertEquals(StoneType.TOPAZ, topaz.getType());
        assertEquals(TopazColor.BLUE, topaz.getColor());
    }

    @Test
    void equalsStoneWithSameProperties() {
        Topaz same = new Topaz(4, 60, 90, 8, TopazColor.BLUE);

        assertEquals(topaz, same);
        assertEquals(topaz.hashCode(), same.hashCode());
    }

    @Test
    void notEqualsWhenPropertyDiffers() {
        assertNotEquals(topaz, new Topaz(4, 60, 91, 8, TopazColor.BLUE));
        assertNotEquals(topaz, new Topaz(4, 60, 90, 8, TopazColor.IMPERIAL));
    }

    @Test
    void describesAllProperties() {
        assertEquals("TOPAZ{carat=4.0, pricePerCarat=60.0, transparency=90.0, hardness=8.0, color=BLUE}",
                topaz.toString());
    }
}
