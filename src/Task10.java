// Найти все пятизначные натуральные числа из заданной последовательности,
// кратные n, первая цифра которых равна пяти, а все цифры различны.
// Подсчитать количество таких чисел.
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите n: ");
        int n = Utils.input(scanner, Integer::parseInt);

        if (n <= 0) {
            System.out.println("n должно быть натуральным.");
            return;
        }

        System.out.println("Введите последовательность чисел в одной строке:");
        List<Integer> numbers = new ArrayList<>();
        Utils.input(numbers, scanner, Integer::parseInt);

        int count = 0;
        System.out.println("Подходящие числа:");

        for (int number : numbers) {
            if (isFiveDigit(number)
                    && isDividedBy(number, n)
                    && isFirstNumberN(number, 5)
                    && hasUniqueDigits(number)) {
                System.out.println(number);
                count++;
            }
        }

        System.out.println("Количество: " + count);
    }

    private static boolean isFiveDigit(int number) {
        return number >= 10_000 && number <= 99_999;
    }

    private static boolean isDividedBy(int number, int n) {
        return number % n == 0;
    }

    private static boolean isFirstNumberN(int number, int n) {
        while (number >= 10) {
            number /= 10;
        }

        return number == n;
    }

    private static boolean hasUniqueDigits(int number) {
        boolean[] usedDigits = new boolean[10];

        while (number > 0) {
            int digit = number % 10;

            if (usedDigits[digit]) {
                return false;
            }

            usedDigits[digit] = true;
            number /= 10;
        }

        return true;
    }
}
