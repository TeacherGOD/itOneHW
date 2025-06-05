package ru.itone.course_java.core.base_syntax;

public class BaseSyntax {

    // Реализовать логическое И
    public boolean and(boolean x, boolean y) {
        return x&&y;
    }

    // Реализовать логическое ИЛИ
    public boolean or(boolean x, boolean y) {
       return x||y;
    }

    // Реализовать логическое СТРОГОЕ ИЛИ
    public boolean xor(boolean x, boolean y) {
        return x!=y;
    }

    // Реализовать сумму двух чисел типа short
    public int sum(short x, short y) {
        return x+y;
    }

    // Реализовать сумму двух чисел типа byte, если результат не вмещается в byte, бросить ошибку (Можно бросить просто RuntimeException)
    public byte sum(byte x, byte y) {
        int res=x+y;
        if (res>Byte.MAX_VALUE|| res <Byte.MIN_VALUE) {
            throw new IllegalArgumentException("byte overflow");
        }
        return (byte) res;
    }

    // Реализовать произведение двух чисел типа short
    public int mult(short x, short y) {
        return x * y;
    }

    // Реализовать произведение двух чисел типа int
    public long mult(int x, int y) {
        return (long) x * y;
    }

    // Реализовать деление двух чисел типа int, если входные данные не корректные, бросить ошибку (Можно бросить просто RuntimeException)
    public int div(int x, int y) {
        if (y == 0) {
            throw new ArithmeticException("Division by zero, but i wrote it by myself");
        }
        return x / y;
    }

    // Реализовать сравнение двух чисел типа float по МОДУЛЮ (без учёта знака), с точностью до 0.0001
    public boolean sameByMod(float x, float y) {
        var xAbs=Math.abs(x);
        var yAbs=Math.abs(y);
        return Math.abs(xAbs-yAbs)<0.0001;
    }

    // Реализовать сложение значения аргументов типа char в число
    public int sumCharValues(char... chars) {
        int sum=0;
        for (char c:chars) sum+=c;
        return sum;
    }

    // Реализовать вычисление факториала натурального числа x. В случае некорректных данных бросить ошибку
    public double factorial(int x) {
        if (x<0){
            throw new IllegalArgumentException("argument must be more then 0");
        }
        double res = 1;
        for (int i = 2; i <= x; i++) {
            res *= i;
        }
        return res;
    }
}
