package org.example.gems;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.gems.comparator.StoneValueComparator;
import org.example.gems.entity.Necklace;
import org.example.gems.entity.Stone;
import org.example.gems.exception.GemException;
import org.example.gems.service.NecklaceAssembler;
import org.example.gems.service.NecklaceCalculator;
import org.example.gems.service.NecklaceSorter;
import org.example.gems.service.StoneFinder;
import org.example.gems.service.StoneLoader;

import java.nio.file.Path;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    private static final Path DEFAULT_DATA_FILE = Path.of("data", "stones.txt");
    private static final String NECKLACE_NAME = "Evening";
    private static final double NECKLACE_BUDGET = 25_000;
    private static final double MIN_TRANSPARENCY = 60;
    private static final double MAX_TRANSPARENCY = 90;

    private Main() {
    }

    public static void main(String[] args) {
        Path dataFile = args.length > 0 ? Path.of(args[0]) : DEFAULT_DATA_FILE;
        try {
            List<Stone> stones = new StoneLoader().load(dataFile);
            Necklace necklace = new NecklaceAssembler().assemble(NECKLACE_NAME, stones, NECKLACE_BUDGET);

            NecklaceCalculator calculator = new NecklaceCalculator();
            logger.info("Total weight: {} ct", calculator.calculateTotalCaratWeight(necklace));
            logger.info("Total value: {}", calculator.calculateTotalValue(necklace));

            new NecklaceSorter().sort(necklace, new StoneValueComparator().reversed());
            necklace.getStones().forEach(stone -> logger.info("  {} value={}", stone, stone.getValue()));

            List<Stone> found = new StoneFinder().findByTransparency(necklace, MIN_TRANSPARENCY, MAX_TRANSPARENCY);
            found.forEach(stone -> logger.info("  {}", stone));
        } catch (GemException e) {
            logger.fatal("Application stopped: {}", e.getMessage(), e);
        }
    }
}
