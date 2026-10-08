package org.example.gems.service;

import org.example.gems.entity.CutType;
import org.example.gems.entity.Diamond;
import org.example.gems.entity.DiamondColor;
import org.example.gems.entity.Opal;
import org.example.gems.entity.Stone;
import org.example.gems.entity.StoneType;
import org.example.gems.exception.GemException;
import org.example.gems.factory.DiamondFactory;
import org.example.gems.factory.StoneFactoryProvider;
import org.example.gems.parser.StoneLineParser;
import org.example.gems.reader.StoneFileReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StoneLoaderTest {
    private static final String DATA = """
            # comment
            DIAMOND; carat=1.2; price=8000; transparency=98; cut=ROUND; color=E
            OPAL; carat=3.2; price=150; transparency=40; hardness=abc; playOfColor=yes
            RUBY; carat=-1; price=3000; transparency=80
            PEARL; carat=1; price=100; transparency=10
            """;

    @TempDir
    Path tempDir;

    @Test
    void loadsValidStonesAndSkipsInvalidLines() throws IOException, GemException {
        Path file = Files.writeString(tempDir.resolve("stones.txt"), DATA);

        List<Stone> stones = new StoneLoader().load(file);

        assertEquals(List.of(
                new Diamond(1.2, 8000, 98, CutType.ROUND, DiamondColor.E),
                new Opal(3.2, 150, 40, 5.5, false)), stones);
    }

    @Test
    void skipsStonesWithoutRegisteredFactory() throws IOException, GemException {
        Path file = Files.writeString(tempDir.resolve("stones.txt"), DATA);
        StoneLoader loader = new StoneLoader(new StoneFileReader(), new StoneLineParser(),
                new StoneFactoryProvider(Map.of(StoneType.DIAMOND, new DiamondFactory())));

        List<Stone> stones = loader.load(file);

        assertEquals(List.of(new Diamond(1.2, 8000, 98, CutType.ROUND, DiamondColor.E)), stones);
    }

    @Test
    void propagatesReadFailure() {
        Path missing = tempDir.resolve("missing.txt");

        assertThrows(GemException.class, () -> new StoneLoader().load(missing));
    }
}
