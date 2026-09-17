import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Task10V2 {
    private static final int Decimals = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите размер матрицы n: ");
        int n = Utils.input(scanner, Integer::parseInt);

        if (n <= 0) {
            System.out.println("n должно быть натуральным.");
            return;
        }

        double[][] matrix = MatrixUtilsV2.readSquare(scanner, n);

        System.out.println("Исходная матрица:");
        MatrixUtilsV2.print(matrix, Decimals);

        matrix = subtractMeanFromMaxElements(matrix);
        System.out.println("После вычитания среднего из максимальных элементов:");
        MatrixUtilsV2.print(matrix, Decimals);

        matrix = sortRowsByDiagonal(matrix);
        System.out.println("После сортировки строк по возрастанию диагональных элементов:");
        MatrixUtilsV2.print(matrix, Decimals);
    }

    private static double[][] subtractMeanFromMaxElements(double[][] matrix) {
        return Arrays.stream(matrix)
                .map(Task10V2::subtractMeanFromMax)
                .toArray(double[][]::new);
    }

    private static double[] subtractMeanFromMax(double[] row) {
        double max = Arrays.stream(row).max().orElseThrow();
        double mean = Arrays.stream(row).average().orElseThrow();

        return Arrays.stream(row)
                .map(value -> value == max ? value - mean : value)
                .toArray();
    }

    private static double[][] sortRowsByDiagonal(double[][] matrix) {
        return IntStream.range(0, matrix.length)
                .boxed()
                .sorted(Comparator.comparingDouble(i -> matrix[i][i]))
                .map(i -> matrix[i])
                .toArray(double[][]::new);
    }
}
