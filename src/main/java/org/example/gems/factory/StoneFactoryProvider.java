package org.example.gems.factory;

import org.example.gems.entity.StoneType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class StoneFactoryProvider {
    private final Map<StoneType, StoneFactory> factories = new EnumMap<>(StoneType.class);

    public StoneFactoryProvider() {
        this(Map.of(
                StoneType.DIAMOND, new DiamondFactory(),
                StoneType.RUBY, new RubyFactory(),
                StoneType.EMERALD, new EmeraldFactory(),
                StoneType.AMETHYST, new AmethystFactory(),
                StoneType.TOPAZ, new TopazFactory(),
                StoneType.OPAL, new OpalFactory()));
    }

    public StoneFactoryProvider(Map<StoneType, StoneFactory> factories) {
        this.factories.putAll(factories);
    }

    public Optional<StoneFactory> getFactory(StoneType type) {
        return Optional.ofNullable(factories.get(type));
    }
}
