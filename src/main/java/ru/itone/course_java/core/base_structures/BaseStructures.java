package ru.itone.course_java.core.base_structures;

import ru.itone.course_java.core.base_structures.geometry.*;
import ru.itone.course_java.core.base_structures.precise.PreciseFloatRecord;

/**
 * Данные методы будут использованы, чтобы получать объекты реализованных вами классов.
 * Вместо ошибок пропишите создание ваших объектов с передачей параметров методов в них
 */
public class BaseStructures {

    public Shape getCircle(float radius) {
        return new Circle(radius);
    }

    public Shape getRectangle(float a, float b) {
        return new Rectangle(a, b);
    }

    public Shape getSquare(float a) {
        return new Square(a);
    }

    public Shape getTriangle(float a, float b, float c) {
        return new Triangle(a, b, c);
    }

    public PreciseFloatRecord getPreciseFloat(int whole, int fractional) {
        return new PreciseFloatRecord(whole, fractional);
    }
}
