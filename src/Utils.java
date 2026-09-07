
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Utils {
    private Utils() {
    }

    public static <T> T input(Scanner scanner, Function<String, T> parser) {
        return parser.apply(scanner.nextLine().trim());
    }

    public static <T> void input(
            List<T> values,
            Scanner scanner,
            Function<String, T> parser
    ) {
        String line = scanner.nextLine().trim();

        if (line.isEmpty()) {
            return;
        }

        for (String value : line.split("\\s+")) {
            values.add(parser.apply(value));
        }
    }
}
