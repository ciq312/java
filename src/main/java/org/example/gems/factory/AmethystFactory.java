package org.example.gems.factory;

import org.example.gems.entity.Amethyst;
import org.example.gems.entity.ColorIntensity;
import org.example.gems.entity.SemiPreciousStone;
import org.example.gems.parser.AttributeKey;
import org.example.gems.parser.StoneAttributes;

public class AmethystFactory implements StoneFactory {
    static final double DEFAULT_HARDNESS = 7;
    static final ColorIntensity DEFAULT_INTENSITY = ColorIntensity.MEDIUM;

    @Override
    public Amethyst createStone(StoneAttributes attributes) {
        return new Amethyst(attributes.getCaratWeight(), attributes.getPricePerCarat(), attributes.getTransparency(),
                attributes.getDouble(AttributeKey.HARDNESS, SemiPreciousStone.MIN_HARDNESS,
                        SemiPreciousStone.MAX_HARDNESS, DEFAULT_HARDNESS),
                attributes.getEnum(AttributeKey.INTENSITY, ColorIntensity.class, DEFAULT_INTENSITY));
    }
}
