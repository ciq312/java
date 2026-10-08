package org.example.gems.factory;

import org.example.gems.entity.CutType;
import org.example.gems.entity.Diamond;
import org.example.gems.entity.DiamondColor;
import org.example.gems.parser.AttributeKey;
import org.example.gems.parser.StoneAttributes;

public class DiamondFactory implements StoneFactory {
    static final CutType DEFAULT_CUT = CutType.ROUND;
    static final DiamondColor DEFAULT_COLOR = DiamondColor.H;

    @Override
    public Diamond createStone(StoneAttributes attributes) {
        return new Diamond(attributes.getCaratWeight(), attributes.getPricePerCarat(), attributes.getTransparency(),
                attributes.getEnum(AttributeKey.CUT, CutType.class, DEFAULT_CUT),
                attributes.getEnum(AttributeKey.COLOR, DiamondColor.class, DEFAULT_COLOR));
    }
}
