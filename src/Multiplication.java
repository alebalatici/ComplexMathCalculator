public class Multiplication implements Operation {
    @Override
    public ComplexNumber execute(ComplexNumber x, ComplexNumber y) {
        double a = x.getReal();
        double b = x.getImag();
        double c = y.getReal();
        double d = y.getImag();
        return new ComplexNumber(a * c - b * d, a * d + b * c);
    }
}