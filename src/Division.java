public class Division implements Operation {
    @Override
    public ComplexNumber execute(ComplexNumber x, ComplexNumber y) {
        double a = x.getReal();
        double b = x.getImag();
        double c = y.getReal();
        double d = y.getImag();
        if (c == 0 && d == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return new ComplexNumber((a * c + b * d) / (c * c + d * d), (b * c - d * a) / (c * c + d * d));
    }
}