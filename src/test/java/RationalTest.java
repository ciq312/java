import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class RationalTest {
    @Test
    void ReducesFraction() {
        Rational fraction = new Rational(6, 8);

        assertEquals(BigInteger.valueOf(3), fraction.GetNumerator());
        assertEquals(BigInteger.valueOf(4), fraction.GetDenominator());
    }

    @Test
    void MovesSignToNumerator() {
        assertEquals(new Rational(-1, 2), new Rational(1, -2));
        assertEquals(new Rational(1, 2), new Rational(-1, -2));
        assertEquals(BigInteger.valueOf(2), new Rational(1, -2).GetDenominator());
    }

    @Test
    void NormalizesZero() {
        Rational zero = new Rational(0, -5);

        assertEquals(Rational.ZERO, zero);
        assertEquals(BigInteger.ONE, zero.GetDenominator());
        assertTrue(zero.IsZero());
    }

    @Test
    void ThrowsOnZeroDenominator() {
        assertThrows(ArithmeticException.class, () -> new Rational(1, 0));
    }

    @Test
    void Adds() {
        assertEquals(new Rational(5, 6), new Rational(1, 2).Add(new Rational(1, 3)));
        assertEquals(Rational.ZERO, new Rational(1, 2).Add(new Rational(-1, 2)));
    }

    @Test
    void Subtracts() {
        assertEquals(new Rational(1, 6), new Rational(1, 2).Subtract(new Rational(1, 3)));
        assertEquals(new Rational(-1, 6), new Rational(1, 3).Subtract(new Rational(1, 2)));
    }

    @Test
    void Multiplies() {
        assertEquals(new Rational(1, 3), new Rational(2, 3).Multiply(new Rational(1, 2)));
        assertEquals(Rational.ZERO, new Rational(2, 3).Multiply(Rational.ZERO));
    }

    @Test
    void Divides() {
        assertEquals(new Rational(4, 3), new Rational(2, 3).Divide(new Rational(1, 2)));
        assertEquals(new Rational(-2), new Rational(1).Divide(new Rational(-1, 2)));
    }

    @Test
    void ThrowsOnDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> Rational.ONE.Divide(Rational.ZERO));
    }

    @Test
    void Negates() {
        assertEquals(new Rational(-3, 4), new Rational(3, 4).Negate());
        assertEquals(Rational.ZERO, Rational.ZERO.Negate());
    }

    @Test
    void Squares() {
        assertEquals(new Rational(4, 9), new Rational(-2, 3).Square());
    }

    @Test
    void KeepsExactValueWhereDoubleLosesIt() {
        Rational tenth = new Rational(1, 10);
        Rational sum = tenth.Add(tenth).Add(tenth);

        assertEquals(new Rational(3, 10), sum);
        assertNotEquals(0.3, 0.1 + 0.1 + 0.1);
    }

    @Test
    void DoesNotOverflowOnLargeValues() {
        Rational max = new Rational(Long.MAX_VALUE);
        BigInteger expected = BigInteger.valueOf(Long.MAX_VALUE).pow(2);

        assertEquals(new Rational(expected, BigInteger.ONE), max.Multiply(max));
    }

    @Test
    void Compares() {
        assertTrue(new Rational(1, 3).compareTo(new Rational(1, 2)) < 0);
        assertTrue(new Rational(-1, 2).compareTo(new Rational(-1, 3)) < 0);
        assertEquals(0, new Rational(2, 4).compareTo(new Rational(1, 2)));
    }

    @Test
    void EqualFractionsHaveEqualHashCodes() {
        assertEquals(new Rational(2, 4).hashCode(), new Rational(1, 2).hashCode());
    }

    @Test
    void ConvertsToDouble() {
        assertEquals(0.5, new Rational(1, 2).ToDouble());
        assertEquals(1.0 / 3, new Rational(1, 3).ToDouble(), 1e-15);
    }

    @Test
    void FormatsAsString() {
        assertEquals("3/4", new Rational(3, 4).toString());
        assertEquals("-1/2", new Rational(1, -2).toString());
        assertEquals("5", new Rational(10, 2).toString());
    }
}
