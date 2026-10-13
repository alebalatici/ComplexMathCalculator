public interface InputExpressionParser {
    boolean isValid(String[] args);
    ComplexNumber parseComplex(String str);
}