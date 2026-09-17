import java.util.Random;

public class Task24 {
    private static final int MinSize = 3;
    private static final int MaxSize = 7;
    private static final double MinValue = -100;
    private static final double MaxValue = 100;
    private static final int Decimals = 1;

    public static void main(String[] args) {
        Random random = new Random();
        int n = random.nextInt(MinSize, MaxSize + 1);
        double[][] matrix = MatrixUtils.generateSquare(random, n, MinValue, MaxValue);

        System.out.println("Исходная матрица " + n + "x" + n + ":");
        MatrixUtils.print(matrix, Decimals);

        arrangeDiagonal(matrix);

        System.out.println("Матрица после перестановки:");
        MatrixUtils.print(matrix, Decimals);
    }

    private static void arrangeDiagonal(double[][] matrix) {
        int n = matrix.length;

        for (int k = 0; k < n; k++) {
            int maxRow = k;
            int maxCol = k;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (isPlacedOnDiagonal(i, j, k)) {
                        continue;
                    }

                    if (matrix[i][j] > matrix[maxRow][maxCol]) {
                        maxRow = i;
                        maxCol = j;
                    }
                }
            }

            MatrixUtils.swap(matrix, k, k, maxRow, maxCol);
        }
    }

    private static boolean isPlacedOnDiagonal(int row, int col, int placedCount) {
        return row == col && row < placedCount;
    }
}
