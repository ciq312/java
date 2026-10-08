package org.example.gems.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.gems.exception.GemException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class StoneFileReader {
    private static final Logger logger = LogManager.getLogger();

    public List<String> readLines(Path path) throws GemException {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            logger.info("Read {} lines from {}", lines.size(), path);
            return lines;
        } catch (IOException e) {
            logger.error("Cannot read file {}", path, e);
            throw new GemException("Cannot read file " + path, e);
        }
    }
}
