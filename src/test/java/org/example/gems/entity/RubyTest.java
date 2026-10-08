package org.example.gems.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RubyTest {
    private final Ruby ruby = new Ruby(2, 3000, 80, CutType.OVAL, true);

    @Test
    void returnsProperties() {
        assertEquals(StoneType.RUBY, ruby.getType());
        assertEquals(CutType.OVAL, ruby.getCut());
        assertTrue(ruby.isHeated());
    }

    @Test
    void equalsStoneWithSameProperties() {
        Ruby same = new Ruby(2, 3000, 80, CutType.OVAL, true);

        assertEquals(ruby, same);
        assertEquals(ruby.hashCode(), same.hashCode());
    }

    @Test
    void notEqualsWhenPropertyDiffers() {
        assertNotEquals(ruby, new Ruby(3, 3000, 80, CutType.OVAL, true));
        assertNotEquals(ruby, new Ruby(2, 3000, 80, CutType.OVAL, false));
    }

    @Test
    void describesAllProperties() {
        assertEquals("RUBY{carat=2.0, pricePerCarat=3000.0, transparency=80.0, cut=OVAL, heated=true}",
                ruby.toString());
    }
}
