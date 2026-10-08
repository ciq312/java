package org.example.gems.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpalTest {
    private final Opal opal = new Opal(2, 200, 35, 6, true);

    @Test
    void returnsProperties() {
        assertEquals(StoneType.OPAL, opal.getType());
        assertTrue(opal.hasPlayOfColor());
    }

    @Test
    void equalsStoneWithSameProperties() {
        Opal same = new Opal(2, 200, 35, 6, true);

        assertEquals(opal, same);
        assertEquals(opal.hashCode(), same.hashCode());
    }

    @Test
    void notEqualsWhenPropertyDiffers() {
        assertNotEquals(opal, new Opal(2, 210, 35, 6, true));
        assertNotEquals(opal, new Opal(2, 200, 35, 6, false));
    }

    @Test
    void describesAllProperties() {
        assertEquals("OPAL{carat=2.0, pricePerCarat=200.0, transparency=35.0, hardness=6.0, playOfColor=true}",
                opal.toString());
    }
}
