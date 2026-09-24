import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    private static Point Of(long x, long y, long z) {
        return new Point(new Rational(x), new Rational(y), new Rational(z));
    }

    private static Point Of(Rational x, Rational y, Rational z) {
        return new Point(x, y, z);
    }

    @Test
    void RejectsNullCoordinates() {
        assertThrows(NullPointerException.class, () -> new Point(null, Rational.ZERO, Rational.ZERO));
    }

    @Test
    void CalculatesDistanceBetweenPoints() {
        Point a = Of(1, 1, 1);
        Point b = Of(3, 4, 7);

        assertEquals(new Rational(49), a.SquaredDistanceTo(b));
        assertEquals(7.0, a.DistanceTo(b));
    }

    @Test
    void CalculatesDistanceWithFractionalCoordinates() {
        Point a = Of(new Rational(1, 2), new Rational(1, 3), Rational.ZERO);
        Point b = Of(new Rational(1), new Rational(2, 3), Rational.ZERO);

        // (1/2)^2 + (1/3)^2 = 13/36
        assertEquals(new Rational(13, 36), a.SquaredDistanceTo(b));
        assertEquals(Math.sqrt(13.0) / 6, a.DistanceTo(b), 1e-12);
    }

    @Test
    void DistanceIsSymmetric() {
        Point a = Of(new Rational(-1, 2), new Rational(5), new Rational(2, 7));
        Point b = Of(new Rational(3), new Rational(-1, 4), new Rational(1));

        assertEquals(a.SquaredDistanceTo(b), b.SquaredDistanceTo(a));
    }

    @Test
    void DistanceToItselfIsZero() {
        Point a = Of(new Rational(1, 3), new Rational(2, 5), new Rational(-7));

        assertEquals(Rational.ZERO, a.SquaredDistanceTo(a));
        assertEquals(0.0, a.DistanceTo(a));
    }

    @Test
    void CalculatesDistanceToOrigin() {
        Point a = Of(2, -3, 6);

        assertEquals(new Rational(49), a.SquaredDistanceToOrigin());
        assertEquals(7.0, a.DistanceToOrigin());
    }

    @Test
    void OriginHasZeroDistanceToOrigin() {
        assertEquals(0.0, Point.ORIGIN.DistanceToOrigin());
    }

    @Test
    void DistanceDoesNotOverflowOnLargeCoordinates() {
        Point a = Of(Long.MAX_VALUE, 0, 0);
        BigInteger expected = BigInteger.valueOf(Long.MAX_VALUE).pow(2);

        assertEquals(new Rational(expected, BigInteger.ONE), a.SquaredDistanceToOrigin());
    }

    @Test
    void DetectsCollinearPoints() {
        assertTrue(Point.AreCollinear(Of(0, 0, 0), Of(1, 2, 3), Of(2, 4, 6)));
        assertTrue(Point.AreCollinear(Of(1, 1, 1), Of(-1, -1, -1), Of(5, 5, 5)));
    }

    @Test
    void DetectsCollinearPointsWithFractions() {
        Point a = Of(new Rational(1, 10), new Rational(2, 10), new Rational(3, 10));
        Point b = Of(new Rational(2, 10), new Rational(4, 10), new Rational(6, 10));
        Point c = Of(new Rational(1, 3), new Rational(2, 3), new Rational(1));

        assertTrue(Point.AreCollinear(a, b, c));
    }

    @Test
    void DetectsNonCollinearPoints() {
        assertFalse(Point.AreCollinear(Of(0, 0, 0), Of(1, 0, 0), Of(0, 1, 0)));
        assertFalse(Point.AreCollinear(Of(0, 0, 0), Of(1, 2, 3), Of(2, 4, 7)));
    }

    @Test
    void DetectsTinyDeviationFromLine() {
        Point a = Of(0, 0, 0);
        Point b = Of(1, 1, 1);
        // смещение на 1e-18 по z: double его не различил бы, дроби различают
        Point c = Of(new Rational(2), new Rational(2), new Rational(2).Add(new Rational(1, 1_000_000_000_000_000_000L)));

        assertFalse(Point.AreCollinear(a, b, c));
    }

    @Test
    void CoincidentPointsAreCollinear() {
        Point a = Of(1, 2, 3);

        assertTrue(Point.AreCollinear(a, a, a));
        assertTrue(Point.AreCollinear(a, a, Of(7, -4, 0)));
    }

    @Test
    void CollinearityDoesNotDependOnOrder() {
        Point a = Of(0, 0, 0);
        Point b = Of(1, 2, 3);
        Point c = Of(2, 4, 6);

        assertTrue(Point.AreCollinear(c, a, b));
        assertTrue(Point.AreCollinear(b, c, a));
    }

    @Test
    void EqualPointsHaveEqualHashCodes() {
        Point a = Of(new Rational(1, 2), new Rational(2), new Rational(3));
        Point b = Of(new Rational(2, 4), new Rational(4, 2), new Rational(3));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void FormatsAsString() {
        assertEquals("(1/2; -3; 0)", Of(new Rational(1, 2), new Rational(-3), Rational.ZERO).toString());
    }
}
