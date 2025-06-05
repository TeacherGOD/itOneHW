package ru.itone.course_java.core.base_structures.geometry;


import lombok.Data;

@Data
public final class Triangle implements Shape {
    private final float a;
    private final float b;
    private final float c;

    public Triangle(float a, float b, float c) {
        if ((a + b > c) && (a + c > b) && (b + c > a)) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        else
            throw new IllegalArgumentException("The triangle("+a+","+b+","+c+") does not exist");
        //А в тестах может быть несуществующий треугольник, так и живём.
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.TRIANGLE;
    }

    @Override
    public double getPerimeter() {
        return a+b+c;
    }

    @Override
    public double getArea() {
        var perimeter=getPerimeter()/2;
        return Math.sqrt(perimeter * (perimeter - a) * (perimeter - b) * (perimeter - c));
    }
}
