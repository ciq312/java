package org.example.gems.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.gems.comparator.StoneValueComparator;
import org.example.gems.entity.Necklace;
import org.example.gems.entity.Stone;

import java.util.ArrayList;
import java.util.List;

public class NecklaceAssembler {
    private static final Logger logger = LogManager.getLogger();

    public Necklace assemble(String name, List<Stone> candidates, double budget) {
        if (!(budget >= 0)) {
            throw new IllegalArgumentException("Budget must be non-negative: " + budget);
        }
        List<Stone> byValueDescending = new ArrayList<>(candidates);
        byValueDescending.sort(new StoneValueComparator().reversed());

        Necklace necklace = new Necklace(name);
        double spent = 0;
        for (Stone stone : byValueDescending) {
            if (spent + stone.getValue() <= budget) {
                necklace.addStone(stone);
                spent += stone.getValue();
            } else {
                logger.debug("{} does not fit the budget, skipped", stone);
            }
        }
        logger.info("Necklace '{}' assembled from {} of {} stones, cost {} of budget {}",
                name, necklace.size(), candidates.size(), spent, budget);
        return necklace;
    }
}
