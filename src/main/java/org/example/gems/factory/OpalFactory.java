package org.example.gems.factory;

import org.example.gems.entity.Opal;
import org.example.gems.entity.SemiPreciousStone;
import org.example.gems.parser.AttributeKey;
import org.example.gems.parser.StoneAttributes;

public class OpalFactory implements StoneFactory {
    static final double DEFAULT_HARDNESS = 5.5;
    static final boolean DEFAULT_PLAY_OF_COLOR = false;

    @Override
    public Opal createStone(StoneAttributes attributes) {
        return new Opal(attributes.getCaratWeight(), attributes.getPricePerCarat(), attributes.getTransparency(),
                attributes.getDouble(AttributeKey.HARDNESS, SemiPreciousStone.MIN_HARDNESS,
                        SemiPreciousStone.MAX_HARDNESS, DEFAULT_HARDNESS),
                attributes.getBoolean(AttributeKey.PLAY_OF_COLOR, DEFAULT_PLAY_OF_COLOR));
    }
}
