import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Task10 {
    private static final int Decimals = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите размер матрицы n: ");
        int n = Utils.input(scanner, Integer::parseInt);

        if (n <= 0) {
            System.out.println("n должно быть натуральным.");
            return;
        }

        double[][] matrix = MatrixUtils.readSquare(scanner, n);

        System.out.println("Исходная матрица:");
        MatrixUtils.print(matrix, Decimals);

        subtractMeanFromMaxElements(matrix);
        System.out.println("После вычитания среднего из максимальных элементов:");
        MatrixUtils.print(matrix, Decimals);

        matrix = sortRowsByDiagonal(matrix);
        System.out.println("После сортировки строк по возрастанию диагональных элементов:");
        MatrixUtils.print(matrix, Decimals);
    }

    private static void subtractMeanFromMaxElements(double[][] matrix) {
        for (double[] row : matrix) {
            double max = row[0];
            double sum = 0;

            for (double value : row) {
                if (value > max) {
                    max = value;
                }

                sum += value;
            }

            double mean = sum / row.length;

            for (int j = 0; j < row.length; j++) {
                if (row[j] == max) {
                    row[j] -= mean;
                }
            }
        }
    }

    private static double[][] sortRowsByDiagonal(double[][] matrix) {
        Integer[] order = new Integer[matrix.length];

        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }

        Arrays.sort(order, Comparator.comparingDouble(i -> matrix[i][i]));

        double[][] sorted = new double[matrix.length][];

        for (int i = 0; i < order.length; i++) {
            sorted[i] = matrix[order[i]];
        }

        return sorted;
    }
}
