package ru.itone.course_java.core.base_structures.precise;

import java.math.BigDecimal;
import java.math.RoundingMode;


public record PreciseFloatRecord(int whole, int fractional) implements PreciseFloat {

    private static final int MAX_FRACTION = 1_000_000;

    public PreciseFloatRecord {
        if (whole < 0 || fractional < 0) {
            throw new IllegalArgumentException("Values must be positive");
        }
        if (fractional >= MAX_FRACTION) {
            throw new IllegalArgumentException("Fractional part max 6 digits");
}
    }

    @Override
    public int getWhole() {
        return whole;
    }

    @Override
    public int getFractional() {
        return fractional;
    }

    private BigDecimal toBigDecimal() {
        return new BigDecimal(whole)
                .add(BigDecimal.valueOf(fractional).movePointLeft(6));
    }

    private static PreciseFloat fromBigDecimal(BigDecimal value) {
        int whole = value.intValue();
        BigDecimal fractional = value.remainder(BigDecimal.ONE).movePointRight(6);
        return new PreciseFloatRecord(whole, fractional.setScale(0, RoundingMode.HALF_UP).intValue());
    }

    @Override
    public PreciseFloat add(PreciseFloat a) {
        BigDecimal result = this.toBigDecimal().add(
                new PreciseFloatRecord(a.getWhole(), a.getFractional()).toBigDecimal()
        );
        return fromBigDecimal(result);
    }

    @Override
    public PreciseFloat subtract(PreciseFloat a) {
        BigDecimal result = this.toBigDecimal().subtract(
                new PreciseFloatRecord(a.getWhole(), a.getFractional()).toBigDecimal()
        );
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArithmeticException("Result is negative");
        }
        return fromBigDecimal(result);
    }

    @Override
    public PreciseFloat multiply(PreciseFloat a) {
        BigDecimal result = this.toBigDecimal().multiply(
                new PreciseFloatRecord(a.getWhole(), a.getFractional()).toBigDecimal()
        );
        return fromBigDecimal(result);
    }

    @Override
    public PreciseFloat divide(PreciseFloat a) {
        if (a.getWhole() == 0 && a.getFractional() == 0) {
            throw new ArithmeticException("Division by zero");
        }
        BigDecimal result = this.toBigDecimal().divide(
                new PreciseFloatRecord(a.getWhole(), a.getFractional()).toBigDecimal(),
                6,
                RoundingMode.HALF_UP
        );
        return fromBigDecimal(result);
    }

    @Override
    public String asString() {
        return whole + "." + String.format("%06d", fractional);
    }
}
