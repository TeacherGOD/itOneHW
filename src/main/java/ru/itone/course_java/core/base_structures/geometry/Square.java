package ru.itone.course_java.core.base_structures.geometry;




public final class Square extends Rectangle {

    public Square(float width) {
        super(width, width);
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.SQUARE;
    }
}
