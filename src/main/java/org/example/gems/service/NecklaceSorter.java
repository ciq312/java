package org.example.gems.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.gems.entity.Necklace;
import org.example.gems.entity.Stone;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NecklaceSorter {
    private static final Logger logger = LogManager.getLogger();

    public void sort(Necklace necklace, Comparator<Stone> comparator) {
        List<Stone> stones = new ArrayList<>(necklace.getStones());
        stones.sort(comparator);
        necklace.setStones(stones);
        logger.info("Necklace '{}' sorted", necklace.getName());
    }
}
