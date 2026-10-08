package org.example.gems.factory;

import org.example.gems.entity.SemiPreciousStone;
import org.example.gems.entity.Topaz;
import org.example.gems.entity.TopazColor;
import org.example.gems.parser.AttributeKey;
import org.example.gems.parser.StoneAttributes;

public class TopazFactory implements StoneFactory {
    static final double DEFAULT_HARDNESS = 8;
    static final TopazColor DEFAULT_COLOR = TopazColor.WHITE;

    @Override
    public Topaz createStone(StoneAttributes attributes) {
        return new Topaz(attributes.getCaratWeight(), attributes.getPricePerCarat(), attributes.getTransparency(),
                attributes.getDouble(AttributeKey.HARDNESS, SemiPreciousStone.MIN_HARDNESS,
                        SemiPreciousStone.MAX_HARDNESS, DEFAULT_HARDNESS),
                attributes.getEnum(AttributeKey.COLOR, TopazColor.class, DEFAULT_COLOR));
    }
}
