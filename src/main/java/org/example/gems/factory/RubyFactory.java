package org.example.gems.factory;

import org.example.gems.entity.CutType;
import org.example.gems.entity.Ruby;
import org.example.gems.parser.AttributeKey;
import org.example.gems.parser.StoneAttributes;

public class RubyFactory implements StoneFactory {
    static final CutType DEFAULT_CUT = CutType.OVAL;
    static final boolean DEFAULT_HEATED = true;

    @Override
    public Ruby createStone(StoneAttributes attributes) {
        return new Ruby(attributes.getCaratWeight(), attributes.getPricePerCarat(), attributes.getTransparency(),
                attributes.getEnum(AttributeKey.CUT, CutType.class, DEFAULT_CUT),
                attributes.getBoolean(AttributeKey.HEATED, DEFAULT_HEATED));
    }
}
