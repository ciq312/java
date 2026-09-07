// Найти и вывести из заданной последовательности натуральных чисел все
// p-значные автоморфные числа. Автоморфное число совпадает с младшими
// цифрами своего квадрата, например: 5^2 = 25, 6^2 = 36, 25^2 = 625.
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task25 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите p: ");
        int p = Utils.input(scanner, Integer::parseInt);

        if (p <= 0) {
            System.out.println("p должно быть положительным.");
            return;
        }

        System.out.println("Введите последовательность чисел в одной строке:");
        List<Integer> numbers = new ArrayList<>();
        Utils.input(numbers, scanner, Integer::parseInt);

        System.out.println("Подходящие автоморфные числа:");

        for (int number : numbers) {
            if (isPDigit(number, p) && isAutomorphic(number)) {
                System.out.println(number);
            }
        }
    }

    private static boolean isPDigit(int number, int p) {
        return number > 0 && String.valueOf(number).length() == p;
    }

    private static boolean isAutomorphic(int number) {
        String numberAsString = String.valueOf(number);
        String squareAsString = String.valueOf((long) number * number);

        return squareAsString.endsWith(numberAsString);
    }
}
