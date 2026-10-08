package org.example.gems.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.gems.entity.Necklace;
import org.example.gems.entity.Stone;

import java.util.List;

public class StoneFinder {
    private static final Logger logger = LogManager.getLogger();

    public List<Stone> findByTransparency(Necklace necklace, double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Min transparency " + min + " is greater than max " + max);
        }
        List<Stone> found = necklace.getStones().stream()
                .filter(stone -> stone.getTransparency() >= min && stone.getTransparency() <= max)
                .toList();
        logger.info("Found {} stones with transparency in [{}, {}]", found.size(), min, max);
        return found;
    }
}
