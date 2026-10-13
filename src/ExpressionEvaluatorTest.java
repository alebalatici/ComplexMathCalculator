import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionEvaluatorTest {
    private InputExpressionParser parser;
    private ExpressionEvaluator evaluator;
    private final double delta = 0.0001;

    @BeforeEach
    public void setUp() {
        parser = new ComplexExpressionParser();
        evaluator = new ExpressionEvaluator(parser);
    }

    @Test
    public void testParseComplex() {
        ComplexNumber number1 = parser.parseComplex("2 + 3*i");
        assertEquals(2, number1.getReal(), delta);
        assertEquals(3, number1.getImag(), delta);

        ComplexNumber number2 = parser.parseComplex("2 - 3*i");
        assertEquals(2, number2.getReal(), delta);
        assertEquals(-3, number2.getImag(), delta);

        ComplexNumber number3 = parser.parseComplex("2 + 1*i");
        assertEquals(2, number3.getReal(), delta);
        assertEquals(1, number3.getImag(), delta);

        ComplexNumber number4 = parser.parseComplex("2 - 1*i");
        assertEquals(2, number4.getReal(), delta);
        assertEquals(-1, number4.getImag(), delta);

        ComplexNumber number5 = parser.parseComplex("2 + i");
        assertEquals(2, number5.getReal(), delta);
        assertEquals(1, number5.getImag(), delta);

        ComplexNumber number6 = parser.parseComplex("2 - i");
        assertEquals(2, number6.getReal(), delta);
        assertEquals(-1, number6.getImag(), delta);

        ComplexNumber number7 = parser.parseComplex("2");
        assertEquals(2, number7.getReal(), delta);
        assertEquals(0, number7.getImag(), delta);

        ComplexNumber number8 = parser.parseComplex("i");
        assertEquals(0, number8.getReal(), delta);
        assertEquals(1, number8.getImag(), delta);

        ComplexNumber number9 = parser.parseComplex("-i");
        assertEquals(0, number9.getReal(), delta);
        assertEquals(-1, number9.getImag(), delta);

        ComplexNumber number10 = parser.parseComplex("1 * i");
        assertEquals(0, number10.getReal(), delta);
        assertEquals(1, number10.getImag(), delta);

        ComplexNumber number11 = parser.parseComplex("- 1 * i");
        assertEquals(0, number11.getReal(), delta);
        assertEquals(-1, number11.getImag(), delta);

        ComplexNumber number12 = parser.parseComplex("-2 + 3 * i");
        assertEquals(-2, number12.getReal(), delta);
        assertEquals(3, number12.getImag(), delta);

        ComplexNumber number13 = parser.parseComplex("-2 - i");
        assertEquals(-2, number13.getReal(), delta);
        assertEquals(-1, number13.getImag(), delta);

        ComplexNumber number14 = parser.parseComplex("2.5 + 3.1 * i");
        assertEquals(2.5, number14.getReal(), delta);
        assertEquals(3.1, number14.getImag(), delta);
    }

    @Test
    public void testOperations() {
        Addition addition = new Addition();
        ComplexNumber number1 = addition.execute(new ComplexNumber(1, 1), new ComplexNumber(1, 1));
        assertEquals(2, number1.getReal(), delta);
        assertEquals(2, number1.getImag(), delta);

        ComplexNumber number1_1 = addition.execute(new ComplexNumber(1, 2), new ComplexNumber(3, 4));
        assertEquals(4, number1_1.getReal(), delta);
        assertEquals(6, number1_1.getImag(), delta);

        Subtraction subtraction = new Subtraction();
        ComplexNumber number2 = subtraction.execute(new ComplexNumber(1, 1), new ComplexNumber(-1, -1));
        assertEquals(2, number2.getReal(), delta);
        assertEquals(2, number2.getImag(), delta);

        Multiplication multiplication = new Multiplication();
        ComplexNumber number3 = multiplication.execute(new ComplexNumber(1, 1), new ComplexNumber(2, 3));
        assertEquals(-1, number3.getReal(), delta);
        assertEquals(5, number3.getImag(), delta);

        Division division = new Division();
        ComplexNumber number4 = division.execute(new ComplexNumber(2, 3), new ComplexNumber(1, 1));
        assertEquals(2.5, number4.getReal(), delta);
        assertEquals(0.5, number4.getImag(), delta);
    }

    @Test
    public void testOperationParser() {
        OperationType addition = OperationType.fromSymbol("+");
        assertEquals(OperationType.ADD, addition);
        Operation operation1 = OperationFactory.getOperation(addition);
        assertInstanceOf(Addition.class, operation1);

        OperationType subtraction = OperationType.fromSymbol("-");
        assertEquals(OperationType.SUBTRACT, subtraction);
        Operation operation2 = OperationFactory.getOperation(subtraction);
        assertInstanceOf(Subtraction.class, operation2);

        OperationType multiplication = OperationType.fromSymbol("*");
        assertEquals(OperationType.MULTIPLY, multiplication);
        Operation operation3 = OperationFactory.getOperation(multiplication);
        assertInstanceOf(Multiplication.class, operation3);

        OperationType division = OperationType.fromSymbol("/");
        assertEquals(OperationType.DIVIDE, division);
        Operation operation4 = OperationFactory.getOperation(division);
        assertInstanceOf(Division.class, operation4);
    }

    @Test
    public void testsSameOperator() {
        String[] args1 = {"50+100*i", "+", "70+60*i", "+", "30+40*i", "+", "50+1*i"};
        ComplexNumber result1 = evaluator.evaluate(args1);
        assertEquals(200, result1.getReal(), delta);
        assertEquals(201, result1.getImag(), delta);

        String[] args2 = {"100+100*i", "+", "-50+50*i", "+", "300-20*i", "+", "-146+70*i", "+", "0+1*i"};
        ComplexNumber result2 = evaluator.evaluate(args2);
        assertEquals(204, result2.getReal(), delta);
        assertEquals(201, result2.getImag(), delta);

        String[] args3 = {"-100+82*i", "+", "300-250*i", "+", "204-250*i"};
        ComplexNumber result3 = evaluator.evaluate(args3);
        assertEquals(404, result3.getReal(), delta);
        assertEquals(-418, result3.getImag(), delta);

        String[] args4 = {"1000+250*i", "-", "582+50*i", "-", "14-1*i"};
        ComplexNumber result4 = evaluator.evaluate(args4);
        assertEquals(404, result4.getReal(), delta);
        assertEquals(201, result4.getImag(), delta);

        String[] args5 = {"1000+0*i", "-", "300+100*i", "-", "200+100*i", "-", "99+51*i", "-", "100+51*i"};
        ComplexNumber result5 = evaluator.evaluate(args5);
        assertEquals(301, result5.getReal(), delta);
        assertEquals(-302, result5.getImag(), delta);

        String[] args6 = {"201+101*i", "*", "1+i"};
        ComplexNumber result6 = evaluator.evaluate(args6);
        assertEquals(100, result6.getReal(), delta);
        assertEquals(302, result6.getImag(), delta);

        String[] args7 = {"91-131*i", "*", "2-i", "*", "1+i"};
        ComplexNumber result7 = evaluator.evaluate(args7);
        assertEquals(404, result7.getReal(), delta);
        assertEquals(-302, result7.getImag(), delta);

        String[] args8 = {"200-100*i", "*", "1+i", "*", "1-i", "*", "0+1*i"};
        ComplexNumber result8 = evaluator.evaluate(args8);
        assertEquals(200, result8.getReal(), delta);
        assertEquals(400, result8.getImag(), delta);

        String[] args9 = {"-3+405*i", "/", "1+i"};
        ComplexNumber result9 = evaluator.evaluate(args9);
        assertEquals(201, result9.getReal(), delta);
        assertEquals(204, result9.getImag(), delta);

        String[] args10 = {"802-836*i", "/", "1+i", "/", "1-i"};
        ComplexNumber result10 = evaluator.evaluate(args10);
        assertEquals(401, result10.getReal(), delta);
        assertEquals(-418, result10.getImag(), delta);

        String[] args11 = {"2508+2418*i", "/", "1+i", "/", "1-i", "/", "3+0*i"};
        ComplexNumber result11 = evaluator.evaluate(args11);
        assertEquals(418, result11.getReal(), delta);
        assertEquals(403, result11.getImag(), delta);

        String[] args12 = {"2+3*i", "+", "5-6*i", "+", "-2+i"};
        ComplexNumber result12 = evaluator.evaluate(args12);
        assertEquals(5, result12.getReal(), delta);
        assertEquals(-2, result12.getImag(), delta);
    }

    @Test
    public void testInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> {evaluator.evaluate(new String[]{});});
        assertThrows(IllegalArgumentException.class, () -> {evaluator.evaluate(new String[]{"2+3*i", "+"});});
        assertThrows(IllegalArgumentException.class, () -> {evaluator.evaluate(new String[]{"+", "2+3*i"});});
        assertThrows(IllegalArgumentException.class, () -> {evaluator.evaluate(new String[]{"2+3*i", "5-6*i"});});
        assertThrows(IllegalArgumentException.class, () -> {evaluator.evaluate(new String[]{"2+3*i", "+", "+", "5-6*i"});});
        assertThrows(IllegalArgumentException.class, () -> {evaluator.evaluate(new String[]{"2+3*j", "+", "1+i"});});
        assertThrows(IllegalArgumentException.class, () -> {evaluator.evaluate(new String[]{"2+3*i", "%", "1+i"});});
        assertThrows(ArithmeticException.class, () -> {evaluator.evaluate(new String[]{"1+i", "/", "0+0*i"});});
        assertThrows(IllegalArgumentException.class, () -> {evaluator.evaluate(new String[]{"2 + 3 * i"});});
    }

    @Test
    public void testMultipleOperators() {
        String[] args1 = {"200+103*i", "+", "350+50*i", "*", "1+i"};
        ComplexNumber result1 = evaluator.evaluate(args1);
        assertEquals(500, result1.getReal(), delta);
        assertEquals(503, result1.getImag(), delta);

        String[] args2 = {"1252-254*i", "-", "250+250*i", "*", "2-i"};
        ComplexNumber result2 = evaluator.evaluate(args2);
        assertEquals(502, result2.getReal(), delta);
        assertEquals(-504, result2.getImag(), delta);

        String[] args3 = {"300+300*i", "+", "-3+405*i", "/", "1+i"};
        ComplexNumber result3 = evaluator.evaluate(args3);
        assertEquals(501, result3.getReal(), delta);
        assertEquals(504, result3.getImag(), delta);

        String[] args4 = {"805+611*i", "-", "200+400*i", "*", "1-i", "/", "2+0*i"};
        ComplexNumber result4 = evaluator.evaluate(args4);
        assertEquals(505, result4.getReal(), delta);
        assertEquals(511, result4.getImag(), delta);

        String[] args5 = {"1000+1000*i", "-", "600+300*i", "+", "100-189*i"};
        ComplexNumber result5 = evaluator.evaluate(args5);
        assertEquals(500, result5.getReal(), delta);
        assertEquals(511, result5.getImag(), delta);

        String[] args6 = {"1400-400*i", "/", "1+i", "/", "1-i", "-", "196+308*i"};
        ComplexNumber result6 = evaluator.evaluate(args6);
        assertEquals(504, result6.getReal(), delta);
        assertEquals(-508, result6.getImag(), delta);

        String[] args7 = {"603+405*i", "+", "201+101*i", "*", "1+i", "-", "-3+405*i", "/", "1+i"};
        ComplexNumber result7 = evaluator.evaluate(args7);
        assertEquals(502, result7.getReal(), delta);
        assertEquals(503, result7.getImag(), delta);
    }

    @Test
    public void testComplexNumberMethods() {
        ComplexNumber c1 = new ComplexNumber(2, 3);
        ComplexNumber c2 = new ComplexNumber(2, 3);
        ComplexNumber c3 = new ComplexNumber(2, -3);

        assertEquals(c1, c2);
        assertNotEquals(c1, c3);

        assertEquals("1+2*i", new ComplexNumber(1, 2).toString());
        assertEquals("-1-2*i", new ComplexNumber(-1, -2).toString());
        assertEquals("2*i", new ComplexNumber(0, 2).toString());
        assertEquals("2", new ComplexNumber(2, 0).toString());
        assertEquals("1.5+2.5*i", new ComplexNumber(1.5, 2.5).toString());
        assertEquals("i", new ComplexNumber(0, 1).toString());
        assertEquals("-i", new ComplexNumber(0, -1).toString());
        assertEquals("-1+i", new ComplexNumber(-1, 1).toString());
        assertEquals("1-i", new ComplexNumber(1, -1).toString());
        assertEquals("1+i", new ComplexNumber(1, 1).toString());
    }

    @Test
    void additionOfFourNumbers() {
        // Arrange
        String[] expression = "-22.5812-7.5792*i + -2.8270-16.9031*i + 24.3977+10.8593*i + 16.4841+46.5735*i".split(" ");
        ComplexNumber expected = new ComplexNumber(15.4736, 32.9505);
        ComplexNumber actual = evaluator.evaluate(expression);

        // Act
        // TODO: parse and evaluate `expression`, then store the result in `actual`.

        // Assert
        assertEquals(expected.getReal(), actual.getReal(), delta);
        assertEquals(expected.getImag(), actual.getImag(), delta);
    }
}