import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixUtilsV2 {
    private MatrixUtilsV2() {
    }

    public static double[][] readSquare(Scanner scanner, int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> readRow(scanner, n, i))
                .toArray(double[][]::new);
    }

    private static double[] readRow(Scanner scanner, int n, int rowIndex) {
        while (true) {
            System.out.printf("Введите строку %d (%d чисел через пробел): ", rowIndex + 1, n);
            List<Double> row = new ArrayList<>();
            Utils.input(row, scanner, Double::parseDouble);

            if (row.size() == n) {
                return row.stream().mapToDouble(Double::doubleValue).toArray();
            }

            System.out.println("Неверное количество чисел, повторите ввод.");
        }
    }

    public static double[][] generateSquare(Random random, int n, double minValue, double maxValue) {
        return IntStream.range(0, n)
                .mapToObj(i -> random.doubles(n, minValue, maxValue)
                        .map(value -> Math.round(value * 10) / 10.0)
                        .toArray())
                .toArray(double[][]::new);
    }

    public static void print(double[][] matrix, int decimals) {
        String format = "%9." + decimals + "f";

        Arrays.stream(matrix)
                .map(row -> Arrays.stream(row)
                        .mapToObj(value -> String.format(format, value))
                        .collect(Collectors.joining()))
                .forEach(System.out::println);
    }
}
