import java.util.Arrays;
import java.util.Comparator;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Task24V2 {
    private static final int MinSize = 3;
    private static final int MaxSize = 7;
    private static final double MinValue = -100;
    private static final double MaxValue = 100;
    private static final int Decimals = 1;

    public static void main(String[] args) {
        Random random = new Random();
        int n = random.nextInt(MinSize, MaxSize + 1);
        double[][] matrix = MatrixUtilsV2.generateSquare(random, n, MinValue, MaxValue);

        System.out.println("Исходная матрица " + n + "x" + n + ":");
        MatrixUtilsV2.print(matrix, Decimals);

        matrix = arrangeDiagonal(matrix);

        System.out.println("Матрица после перестановки:");
        MatrixUtilsV2.print(matrix, Decimals);
    }

    private static double[][] arrangeDiagonal(double[][] matrix) {
        int n = matrix.length;
        double[] flat = Arrays.stream(matrix).flatMapToDouble(Arrays::stream).toArray();

        int[] largestCells = IntStream.range(0, flat.length)
                .boxed()
                .sorted(Comparator.comparingDouble((Integer cell) -> flat[cell]).reversed())
                .limit(n)
                .mapToInt(Integer::intValue)
                .toArray();

        Set<Integer> largestSet = Arrays.stream(largestCells).boxed().collect(Collectors.toSet());

        PrimitiveIterator.OfDouble rest = IntStream.range(0, flat.length)
                .filter(cell -> !largestSet.contains(cell))
                .mapToDouble(cell -> flat[cell])
                .iterator();

        return IntStream.range(0, n)
                .mapToObj(i -> IntStream.range(0, n)
                        .mapToDouble(j -> i == j ? flat[largestCells[i]] : rest.nextDouble())
                        .toArray())
                .toArray(double[][]::new);
    }
}
