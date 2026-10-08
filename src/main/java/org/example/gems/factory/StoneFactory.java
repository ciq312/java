package org.example.gems.factory;

import org.example.gems.entity.Stone;
import org.example.gems.parser.StoneAttributes;

public interface StoneFactory {
    Stone createStone(StoneAttributes attributes);
}
