package ru.itone.course_java.core.base_structures.geometry;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import ru.itone.course_java.core.base_structures.BaseStructures;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class BaseStructuresTest {

    BaseStructures baseStructures = new BaseStructures();
    Random random = new Random();

    @Test
    void getCircle() {
        var r = random.nextFloat(1, 100);
        var circle = baseStructures.getCircle(r);

        assertThat(circle.getShapeType()).isEqualTo(ShapeType.CIRCLE);
        assertThat(circle.getArea()).isCloseTo(Math.pow(r, 2) * Math.PI, Offset.offset(0.01));
        assertThat(circle.getPerimeter()).isCloseTo(2 * r * Math.PI, Offset.offset(0.01));
    }

    @Test
    void getRectangle() {
        var a = random.nextFloat(1, 100);
        var b = random.nextFloat(1, 100);
        var rectangle = baseStructures.getRectangle(a, b);

        assertThat(rectangle.getShapeType()).isEqualTo(ShapeType.RECTANGLE);
        assertThat(rectangle.getArea()).isCloseTo(a * b, Offset.offset(0.01));
        assertThat(rectangle.getPerimeter()).isCloseTo(a + a + b + b, Offset.offset(0.01));
    }

    @Test
    void getSquare() {
        var a = random.nextFloat(1, 100);
        var square = baseStructures.getSquare(a);

        assertThat(square.getShapeType()).isEqualTo(ShapeType.SQUARE);
        assertThat(square.getArea()).isCloseTo(a * a, Offset.offset(0.01));
        assertThat(square.getPerimeter()).isCloseTo(4 * a, Offset.offset(0.01));
    }

    @Test
    void getTriangle() {
        float a,b,c; //Исправил несуществующий треугольник.
        do {
            a = random.nextFloat(1, 100);
            b = random.nextFloat(1, 100);
            c = random.nextFloat(1, 100);
        } while (!(a + b > c && a + c > b && b + c > a));
        var triangle = baseStructures.getTriangle(a, b, c);

        assertThat(triangle.getShapeType()).isEqualTo(ShapeType.TRIANGLE);
        float p = a + b + c;
        assertThat(triangle.getPerimeter()).isCloseTo(p, Offset.offset(0.01));
        p /= 2;
        assertThat(triangle.getArea()).isCloseTo(Math.sqrt(p * (p - a) * (p - b) * (p - c)), Offset.offset(0.01));
    }
}