package ru.itone.course_java.core.base_structures.geometry;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Circle implements Shape{

    private final float radius;
    @Override
    public double getArea() {
        return Math.PI*radius*radius;
    }

    @Override
    public double getPerimeter() {
        return Math.PI*2*radius;
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.CIRCLE;
    }
}
