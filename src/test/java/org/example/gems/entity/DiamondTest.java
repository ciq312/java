package org.example.gems.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiamondTest {
    private final Diamond diamond = new Diamond(1.2, 8000, 98, CutType.ROUND, DiamondColor.E);

    @Test
    void returnsProperties() {
        assertEquals(StoneType.DIAMOND, diamond.getType());
        assertEquals(1.2, diamond.getCaratWeight());
        assertEquals(8000, diamond.getPricePerCarat());
        assertEquals(98, diamond.getTransparency());
        assertEquals(CutType.ROUND, diamond.getCut());
        assertEquals(DiamondColor.E, diamond.getColor());
        assertEquals(9600, diamond.getValue(), 1e-9);
    }

    @Test
    void equalsStoneWithSameProperties() {
        Diamond same = new Diamond(1.2, 8000, 98, CutType.ROUND, DiamondColor.E);

        assertEquals(diamond, same);
        assertEquals(diamond.hashCode(), same.hashCode());
    }

    @Test
    void equalsItself() {
        assertTrue(diamond.equals(diamond));
    }

    @Test
    void notEqualsNullOrOtherClass() {
        assertFalse(diamond.equals(null));
        assertFalse(diamond.equals("diamond"));
        assertNotEquals(diamond, new Ruby(1.2, 8000, 98, CutType.ROUND, false));
    }

    @Test
    void notEqualsWhenCommonPropertyDiffers() {
        assertNotEquals(diamond, new Diamond(1.3, 8000, 98, CutType.ROUND, DiamondColor.E));
        assertNotEquals(diamond, new Diamond(1.2, 8100, 98, CutType.ROUND, DiamondColor.E));
        assertNotEquals(diamond, new Diamond(1.2, 8000, 97, CutType.ROUND, DiamondColor.E));
    }

    @Test
    void notEqualsWhenCutDiffers() {
        assertNotEquals(diamond, new Diamond(1.2, 8000, 98, CutType.PEAR, DiamondColor.E));
    }

    @Test
    void notEqualsWhenColorDiffers() {
        assertNotEquals(diamond, new Diamond(1.2, 8000, 98, CutType.ROUND, DiamondColor.D));
    }

    @Test
    void describesAllProperties() {
        assertEquals("DIAMOND{carat=1.2, pricePerCarat=8000.0, transparency=98.0, cut=ROUND, color=E}",
                diamond.toString());
    }
}
