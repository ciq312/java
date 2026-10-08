package org.example.gems.reader;

import org.example.gems.exception.GemException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StoneFileReaderTest {
    private final StoneFileReader reader = new StoneFileReader();

    @TempDir
    Path tempDir;

    @Test
    void readsAllLines() throws IOException, GemException {
        Path file = Files.writeString(tempDir.resolve("stones.txt"), "first\nsecond\n");

        assertEquals(List.of("first", "second"), reader.readLines(file));
    }

    @Test
    void throwsGemExceptionForMissingFile() {
        Path missing = tempDir.resolve("missing.txt");

        GemException exception = assertThrows(GemException.class, () -> reader.readLines(missing));
        assertInstanceOf(IOException.class, exception.getCause());
    }
}
