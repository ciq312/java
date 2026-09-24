import java.util.Objects;

public final class Point {
    public static final Point ORIGIN = new Point(Rational.ZERO, Rational.ZERO, Rational.ZERO);

    private final Rational x;
    private final Rational y;
    private final Rational z;

    public Point(Rational x, Rational y, Rational z) {
        this.x = Objects.requireNonNull(x, "x");
        this.y = Objects.requireNonNull(y, "y");
        this.z = Objects.requireNonNull(z, "z");
    }

    public Rational GetX() {
        return x;
    }

    public Rational GetY() {
        return y;
    }

    public Rational GetZ() {
        return z;
    }

    public Rational SquaredDistanceTo(Point other) {
        return other.x.Subtract(x).Square()
                .Add(other.y.Subtract(y).Square())
                .Add(other.z.Subtract(z).Square());
    }

    public double DistanceTo(Point other) {
        return Math.sqrt(SquaredDistanceTo(other).ToDouble());
    }

    public Rational SquaredDistanceToOrigin() {
        return SquaredDistanceTo(ORIGIN);
    }

    public double DistanceToOrigin() {
        return DistanceTo(ORIGIN);
    }

    public static boolean AreCollinear(Point a, Point b, Point c) {
        Rational abX = b.x.Subtract(a.x);
        Rational abY = b.y.Subtract(a.y);
        Rational abZ = b.z.Subtract(a.z);
        Rational acX = c.x.Subtract(a.x);
        Rational acY = c.y.Subtract(a.y);
        Rational acZ = c.z.Subtract(a.z);

        Rational crossX = abY.Multiply(acZ).Subtract(abZ.Multiply(acY));
        Rational crossY = abZ.Multiply(acX).Subtract(abX.Multiply(acZ));
        Rational crossZ = abX.Multiply(acY).Subtract(abY.Multiply(acX));

        return crossX.IsZero() && crossY.IsZero() && crossZ.IsZero();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Point other)) {
            return false;
        }
        return x.equals(other.x) && y.equals(other.y) && z.equals(other.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + "; " + z + ")";
    }
}
