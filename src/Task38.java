// Для каждого числа из заданной последовательности натуральных чисел определить,
// верно ли, что куб суммы его цифр равен квадрату самого числа.
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task38 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите последовательность натуральных чисел в одной строке:");
        List<Integer> numbers = new ArrayList<>();
        Utils.input(numbers, scanner, Integer::parseInt);

        for (int number : numbers) {
            System.out.println(number + ": " + (isConditionTrue(number) ? "да" : "нет"));
        }
    }

    private static boolean isConditionTrue(int number) {
        if (number <= 0) {
            return false;
        }

        long digitsSum = sumDigits(number);
        long digitsSumCube = digitsSum * digitsSum * digitsSum;
        long numberSquare = (long) number * number;

        return digitsSumCube == numberSquare;
    }

    private static int sumDigits(int number) {
        int sum = 0;

        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }

        return sum;
    }
}
