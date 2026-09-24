/*
 * Лабораторная работа №3. Вариант 6.
 *
 * Определить класс Точка в пространстве, координаты которой задаются
 * с помощью Рациональной Дроби.
 *
 * Создать методы:
 *   - определения расстояния между точками;
 *   - определения расстояния до начала координат.
 *
 * Проверить для трёх точек возможность нахождения на одной прямой.
 */
public class Main {
    public static void main(String[] args) {
        Point a = new Point(new Rational(1, 2), new Rational(1, 3), new Rational(1));
        Point b = new Point(new Rational(1), new Rational(2, 3), new Rational(2));
        Point c = new Point(new Rational(3, 2), new Rational(1), new Rational(3));
        Point d = new Point(new Rational(2), new Rational(3), new Rational(6));

        System.out.println("A = " + a);
        System.out.println("B = " + b);
        System.out.println("C = " + c);
        System.out.println("D = " + d);
        System.out.println();

        System.out.println("|AB|^2 = " + a.SquaredDistanceTo(b) + ", |AB| ≈ " + a.DistanceTo(b));
        System.out.println("|OD|^2 = " + d.SquaredDistanceToOrigin() + ", |OD| = " + d.DistanceToOrigin());
        System.out.println();

        System.out.println("A, B, C на одной прямой: " + Point.AreCollinear(a, b, c));
        System.out.println("A, B, D на одной прямой: " + Point.AreCollinear(a, b, d));
    }
}
