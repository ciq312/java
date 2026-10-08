package org.example.gems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MainTest {
    @Test
    void runsWithDefaultDataFile() {
        assertDoesNotThrow(() -> Main.main(new String[0]));
    }

    @Test
    void handlesMissingDataFile() {
        assertDoesNotThrow(() -> Main.main(new String[]{"no/such/file.txt"}));
    }
}
