package ru.itone.course_java.core.base_structures.precise;

import org.junit.jupiter.api.Test;
import ru.itone.course_java.core.base_structures.BaseStructures;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PreciseFloatTest {

    BaseStructures baseStructures = new BaseStructures();
    Random random = new Random();

    @Test
    void getWholeAndFractional() {
        var w = random.nextInt(0,Integer.MAX_VALUE); //сделал числа положительными, если надо просто отбрасывать отрицательную часть - ну стоило внятно донести
        var f = random.nextInt(0,1_000_000); //добавил верхнее ограничение к дробной части.
        var p = baseStructures.getPreciseFloat(w, f);

        assertThat(p.getWhole()).isEqualTo(w);
        assertThat(p.getFractional()).isEqualTo(f);
    }

    @Test
    void add_SmallFractional() {
        var w1 = random.nextInt(Integer.MAX_VALUE / 2);
        var f1 = random.nextInt(1, 1000);
        var p1 = baseStructures.getPreciseFloat(w1, f1);

        var w2 = random.nextInt(Integer.MAX_VALUE / 2);
        var f2 = random.nextInt(1, 1000);
        var p2 = baseStructures.getPreciseFloat(w2, f2);

        var b1 = new BigDecimal("%d.%06d".formatted(w1, f1));
        var b2 = new BigDecimal("%d.%06d".formatted(w2, f2));

        var r = p1.add(p2);
        var rb = b1.add(b2);

        assertThat(r != p1 && r != p2).isTrue();
        assertThat(r.asString()).isEqualTo(rb.toString());
    }

    @Test
    void add() {
        var w1 = random.nextInt(Integer.MAX_VALUE / 2);
        var f1 = random.nextInt(1_000_000 / 2 + 1, 1_000_000);
        var p1 = baseStructures.getPreciseFloat(w1, f1);

        var w2 = random.nextInt(Integer.MAX_VALUE / 2);
        var f2 = random.nextInt(1_000_000 / 2 + 1, 1_000_000);
        var p2 = baseStructures.getPreciseFloat(w2, f2);

        var b1 = new BigDecimal("%d.%06d".formatted(w1, f1));
        var b2 = new BigDecimal("%d.%06d".formatted(w2, f2));

        var r = p1.add(p2);
        var rb = b1.add(b2);

        assertThat(r != p1 && r != p2).isTrue();
        assertThat(r.asString()).isEqualTo(rb.toString());
    }

    @Test
    void subtract() {
        var w1 = random.nextInt(Integer.MAX_VALUE / 2 + 1, Integer.MAX_VALUE);
        var f1 = random.nextInt(1, 1_000_000 / 2);
        var p1 = baseStructures.getPreciseFloat(w1, f1);

        var w2 = random.nextInt(Integer.MAX_VALUE / 2);
        var f2 = random.nextInt(1_000_000 / 2 + 1, 1_000_000);
        var p2 = baseStructures.getPreciseFloat(w2, f2);

        var b1 = new BigDecimal("%d.%06d".formatted(w1, f1));
        var b2 = new BigDecimal("%d.%06d".formatted(w2, f2));

        var r = p1.subtract(p2);
        var rb = b1.subtract(b2);

        assertThat(r != p1 && r != p2).isTrue();
        assertThat(r.asString()).isEqualTo(rb.toString());
    }

    @Test
    void multiply() {
        var w1 = random.nextInt(1, 10_000);
        var f1 = random.nextInt(1_000_000 / 2 + 1, 1_000_000);
        var p1 = baseStructures.getPreciseFloat(w1, f1);

        var w2 = random.nextInt(1, 10_000);
        var f2 = random.nextInt(1_000_000 / 2 + 1, 1_000_000);
        var p2 = baseStructures.getPreciseFloat(w2, f2);

        var b1 = new BigDecimal("%d.%06d".formatted(w1, f1));
        var b2 = new BigDecimal("%d.%06d".formatted(w2, f2));

        var r = p1.multiply(p2);
        var rb = b1.multiply(b2).setScale(6,RoundingMode.HALF_UP); //добавил округление до 6 знака.


        assertThat(r != p1 && r != p2).isTrue();
        assertThat(r.asString()).isEqualTo(rb.toString());
    }

    @Test
    void divide() {
        var w1 = random.nextInt(Integer.MAX_VALUE / 2, Integer.MAX_VALUE);
        var f1 = random.nextInt(1_000_000 / 2 + 1, 1_000_000);
        var p1 = baseStructures.getPreciseFloat(w1, f1);

        var w2 = random.nextInt(Integer.MAX_VALUE / 2);
        var f2 = random.nextInt(1_000_000 / 2 + 1, 1_000_000);
        var p2 = baseStructures.getPreciseFloat(w2, f2);

        var b1 = new BigDecimal("%d.%06d".formatted(w1, f1));
        var b2 = new BigDecimal("%d.%06d".formatted(w2, f2));

        var r = p1.divide(p2);
        var rb = b1.divide(b2, RoundingMode.HALF_UP);

        assertThat(r != p1 && r != p2).isTrue();
        assertThat(r.asString()).isEqualTo(rb.toString());
    }
}
