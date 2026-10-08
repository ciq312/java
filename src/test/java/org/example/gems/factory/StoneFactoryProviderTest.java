package org.example.gems.factory;

import org.example.gems.entity.StoneType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoneFactoryProviderTest {
    @ParameterizedTest
    @EnumSource(StoneType.class)
    void providesFactoryForEveryTypeByDefault(StoneType type) {
        assertTrue(new StoneFactoryProvider().getFactory(type).isPresent());
    }

    @Test
    void providesOnlyRegisteredFactories() {
        StoneFactory factory = new DiamondFactory();
        StoneFactoryProvider provider = new StoneFactoryProvider(Map.of(StoneType.DIAMOND, factory));

        assertEquals(Optional.of(factory), provider.getFactory(StoneType.DIAMOND));
        assertEquals(Optional.empty(), provider.getFactory(StoneType.OPAL));
    }
}
