package org.example.gems.factory;

import org.example.gems.entity.Amethyst;
import org.example.gems.entity.ColorIntensity;
import org.example.gems.entity.CutType;
import org.example.gems.entity.Diamond;
import org.example.gems.entity.DiamondColor;
import org.example.gems.entity.Emerald;
import org.example.gems.entity.Opal;
import org.example.gems.entity.Ruby;
import org.example.gems.entity.StoneType;
import org.example.gems.entity.Topaz;
import org.example.gems.entity.TopazColor;
import org.example.gems.parser.StoneAttributes;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StoneFactoryTest {
    private static StoneAttributes attributes(StoneType type, Map<String, String> fields) {
        return new StoneAttributes(type, 1.0, 100.0, 50.0, fields);
    }

    @Test
    void createsDiamond() {
        Diamond diamond = new DiamondFactory().createStone(
                attributes(StoneType.DIAMOND, Map.of("cut", "PRINCESS", "color", "D")));

        assertEquals(new Diamond(1, 100, 50, CutType.PRINCESS, DiamondColor.D), diamond);
    }

    @Test
    void createsDiamondWithDefaults() {
        Diamond diamond = new DiamondFactory().createStone(
                attributes(StoneType.DIAMOND, Map.of("cut", "TRIANGLE", "color", "Z")));

        assertEquals(DiamondFactory.DEFAULT_CUT, diamond.getCut());
        assertEquals(DiamondFactory.DEFAULT_COLOR, diamond.getColor());
    }

    @Test
    void createsRuby() {
        Ruby ruby = new RubyFactory().createStone(
                attributes(StoneType.RUBY, Map.of("cut", "CUSHION", "heated", "false")));

        assertEquals(new Ruby(1, 100, 50, CutType.CUSHION, false), ruby);
    }

    @Test
    void createsRubyWithDefaults() {
        Ruby ruby = new RubyFactory().createStone(attributes(StoneType.RUBY, Map.of()));

        assertEquals(new Ruby(1, 100, 50, RubyFactory.DEFAULT_CUT, RubyFactory.DEFAULT_HEATED), ruby);
    }

    @Test
    void createsEmerald() {
        Emerald emerald = new EmeraldFactory().createStone(
                attributes(StoneType.EMERALD, Map.of("cut", "OVAL", "origin", "Zambia")));

        assertEquals(new Emerald(1, 100, 50, CutType.OVAL, "Zambia"), emerald);
    }

    @Test
    void createsEmeraldWithDefaults() {
        Emerald emerald = new EmeraldFactory().createStone(attributes(StoneType.EMERALD, Map.of("origin", "")));

        assertEquals(new Emerald(1, 100, 50, EmeraldFactory.DEFAULT_CUT, EmeraldFactory.DEFAULT_ORIGIN), emerald);
    }

    @Test
    void createsAmethyst() {
        Amethyst amethyst = new AmethystFactory().createStone(
                attributes(StoneType.AMETHYST, Map.of("hardness", "6.5", "intensity", "LIGHT")));

        assertEquals(new Amethyst(1, 100, 50, 6.5, ColorIntensity.LIGHT), amethyst);
    }

    @Test
    void createsAmethystWithDefaults() {
        Amethyst amethyst = new AmethystFactory().createStone(
                attributes(StoneType.AMETHYST, Map.of("hardness", "0", "intensity", "BRIGHT")));

        assertEquals(new Amethyst(1, 100, 50, AmethystFactory.DEFAULT_HARDNESS, AmethystFactory.DEFAULT_INTENSITY),
                amethyst);
    }

    @Test
    void createsTopaz() {
        Topaz topaz = new TopazFactory().createStone(
                attributes(StoneType.TOPAZ, Map.of("hardness", "8", "color", "imperial")));

        assertEquals(new Topaz(1, 100, 50, 8, TopazColor.IMPERIAL), topaz);
    }

    @Test
    void createsTopazWithDefaults() {
        Topaz topaz = new TopazFactory().createStone(
                attributes(StoneType.TOPAZ, Map.of("hardness", "15", "color", "PURPLE")));

        assertEquals(new Topaz(1, 100, 50, TopazFactory.DEFAULT_HARDNESS, TopazFactory.DEFAULT_COLOR), topaz);
    }

    @Test
    void createsOpal() {
        Opal opal = new OpalFactory().createStone(
                attributes(StoneType.OPAL, Map.of("hardness", "6", "playofcolor", "true")));

        assertEquals(new Opal(1, 100, 50, 6, true), opal);
    }

    @Test
    void createsOpalWithDefaults() {
        Opal opal = new OpalFactory().createStone(
                attributes(StoneType.OPAL, Map.of("hardness", "abc", "playofcolor", "yes")));

        assertEquals(new Opal(1, 100, 50, OpalFactory.DEFAULT_HARDNESS, OpalFactory.DEFAULT_PLAY_OF_COLOR), opal);
    }
}
