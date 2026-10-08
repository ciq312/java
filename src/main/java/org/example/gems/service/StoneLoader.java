package org.example.gems.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.gems.entity.Stone;
import org.example.gems.exception.GemException;
import org.example.gems.factory.StoneFactory;
import org.example.gems.factory.StoneFactoryProvider;
import org.example.gems.parser.StoneAttributes;
import org.example.gems.parser.StoneLineParser;
import org.example.gems.reader.StoneFileReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoneLoader {
    private static final Logger logger = LogManager.getLogger();

    private final StoneFileReader reader;
    private final StoneLineParser parser;
    private final StoneFactoryProvider factoryProvider;

    public StoneLoader() {
        this(new StoneFileReader(), new StoneLineParser(), new StoneFactoryProvider());
    }

    public StoneLoader(StoneFileReader reader, StoneLineParser parser, StoneFactoryProvider factoryProvider) {
        this.reader = reader;
        this.parser = parser;
        this.factoryProvider = factoryProvider;
    }

    public List<Stone> load(Path path) throws GemException {
        List<Stone> stones = new ArrayList<>();
        for (String line : reader.readLines(path)) {
            parser.parse(line).flatMap(this::createStone).ifPresent(stones::add);
        }
        logger.info("Loaded {} stones from {}", stones.size(), path);
        return stones;
    }

    private Optional<Stone> createStone(StoneAttributes attributes) {
        Optional<StoneFactory> factory = factoryProvider.getFactory(attributes.getType());
        if (factory.isEmpty()) {
            logger.error("No factory registered for {}, stone skipped", attributes.getType());
            return Optional.empty();
        }
        Stone stone = factory.get().createStone(attributes);
        logger.debug("Created {}", stone);
        return Optional.of(stone);
    }
}
