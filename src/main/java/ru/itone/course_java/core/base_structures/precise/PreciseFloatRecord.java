package ru.itone.course_java.core.base_structures.precise;

import static java.util.Objects.isNull;

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

    @Override
    public PreciseFloat add(PreciseFloat a) {
        if (isNull(a)) throw new IllegalArgumentException("Argument cannot be null");
        var sumFract=a.getFractional()+getFractional();
        int toAdd=sumFract/MAX_FRACTION;
        var resFract=sumFract%MAX_FRACTION;
        int resWhole= getWhole()+a.getWhole()+toAdd;
        return new PreciseFloatRecord(resWhole,resFract);
    }

    @Override
    public PreciseFloat subtract(PreciseFloat a) {
        if (isNull(a)) throw new IllegalArgumentException("Argument cannot be null");

        var newFract= getFractional()-a.getFractional();

        var newWhole=getWhole();
        if (newFract<0) {
            newFract=MAX_FRACTION+newFract;
            newWhole-=1;
        }
        newWhole-=a.getWhole();

        if (newWhole<0) {
            throw new ArithmeticException("Res is Negative");
        }



        return new PreciseFloatRecord(newWhole,newFract);
    }



    @Override
    public PreciseFloat multiply(PreciseFloat a) {
        if (isNull(a)) throw new IllegalArgumentException("Argument cannot be null");

        long resultWhole = (long) getWhole() *a.getWhole();
        long middlePart = (long) a.getWhole() * getFractional() + (long) getWhole() * a.getFractional();
        long fractPart = (long) getFractional() * a.getFractional();
        long resultFract = middlePart + fractPart / MAX_FRACTION;

        resultWhole += resultFract / MAX_FRACTION;
        resultFract = resultFract % MAX_FRACTION;

        long remainder = fractPart % MAX_FRACTION;
        if (remainder * 2 >= MAX_FRACTION) {
            resultFract++;
            if (resultFract >= MAX_FRACTION) {
                resultFract -= MAX_FRACTION;
                resultWhole++;
            }
        }

        return new PreciseFloatRecord((int) resultWhole, (int) resultFract);

    }

    @Override
    public PreciseFloat divide(PreciseFloat a) {
        if (isNull(a)) throw new IllegalArgumentException("Argument cannot be null");

        if (a.getWhole() == 0 && a.getFractional() == 0) {
            throw new ArithmeticException("Division by zero");
        }
        long divisor = (long) a.getWhole() * MAX_FRACTION + a.getFractional();
        long dividend = (long) getWhole() * MAX_FRACTION + fractional;
        long resultWhole = dividend / divisor;
        long remainder = dividend % divisor;
        long resultFrac = calcFractPart(remainder, divisor);
        long remainderFrac = (remainder * MAX_FRACTION) % divisor;
        if (remainderFrac * 2 >= divisor) {
            resultFrac++;
            if (resultFrac >= MAX_FRACTION) {
                resultFrac -= MAX_FRACTION;
                resultWhole++;
            }
        }

        return new PreciseFloatRecord((int) resultWhole, (int) resultFrac);
    }

    private int calcFractPart(long remainder, long divisor) {
        int resultFrac = 0; //делим столбиком
        for (int i = 0; i < String.valueOf(MAX_FRACTION).length() - 1; i++) {
            remainder *= 10;
            int digit = (int)(remainder / divisor);
            remainder = remainder % divisor;
            resultFrac = resultFrac * 10 + digit;
        }
        remainder *= 10;
        int nextDigit = (int)(remainder / divisor);
        if (nextDigit >= 5) {
            resultFrac++;
        }
        return resultFrac;
    }

    @Override
    public String asString() {
        return whole + "." + String.format("%06d", fractional);
    }
}
