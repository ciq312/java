import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MatrixUtils {
    private MatrixUtils() {
    }

    public static double[][] readSquare(Scanner scanner, int n) {
        double[][] matrix = new double[n][];

        for (int i = 0; i < n; i++) {
            System.out.printf("Введите строку %d (%d чисел через пробел): ", i + 1, n);
            List<Double> row = new ArrayList<>();
            Utils.input(row, scanner, Double::parseDouble);

            if (row.size() != n) {
                System.out.println("Неверное количество чисел, повторите ввод.");
                i--;
                continue;
            }

            matrix[i] = new double[n];

            for (int j = 0; j < n; j++) {
                matrix[i][j] = row.get(j);
            }
        }

        return matrix;
    }

    public static double[][] generateSquare(Random random, int n, double minValue, double maxValue) {
        double[][] matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Math.round(random.nextDouble(minValue, maxValue) * 10) / 10.0;
            }
        }

        return matrix;
    }

    public static void swap(double[][] matrix, int row1, int col1, int row2, int col2) {
        double temp = matrix[row1][col1];
        matrix[row1][col1] = matrix[row2][col2];
        matrix[row2][col2] = temp;
    }

    public static void print(double[][] matrix, int decimals) {
        String format = "%9." + decimals + "f";

        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf(format, value);
            }

            System.out.println();
        }
    }
}
