package org.example.gems.factory;

import org.example.gems.entity.CutType;
import org.example.gems.entity.Emerald;
import org.example.gems.parser.AttributeKey;
import org.example.gems.parser.StoneAttributes;

public class EmeraldFactory implements StoneFactory {
    static final CutType DEFAULT_CUT = CutType.EMERALD;
    static final String DEFAULT_ORIGIN = "Unknown";

    @Override
    public Emerald createStone(StoneAttributes attributes) {
        return new Emerald(attributes.getCaratWeight(), attributes.getPricePerCarat(), attributes.getTransparency(),
                attributes.getEnum(AttributeKey.CUT, CutType.class, DEFAULT_CUT),
                attributes.getString(AttributeKey.ORIGIN, DEFAULT_ORIGIN));
    }
}
