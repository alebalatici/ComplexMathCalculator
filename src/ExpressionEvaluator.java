import java.util.ArrayList;
import java.util.List;

public class ExpressionEvaluator {
    private final InputExpressionParser parser;

    public ExpressionEvaluator(InputExpressionParser parser) {
        this.parser = parser;
    }

    public ComplexNumber evaluate(String[] args) {
        if (!parser.isValid(args)) {
            throw new IllegalArgumentException("Invalid expression");
        }

        List<ComplexNumber> numbers = new ArrayList<>();
        List<OperationType> operators = new ArrayList<>();

        numbers.add(parser.parseComplex(args[0]));
        for (int i = 1; i < args.length; i += 2) {
            operators.add(OperationType.fromSymbol(args[i]));
            numbers.add(parser.parseComplex(args[i + 1]));
        }

        for (int i = 0; i < operators.size();) {
            OperationType operationType = operators.get(i);
            if (operationType == OperationType.MULTIPLY || operationType == OperationType.DIVIDE) {
                Operation operation = OperationFactory.getOperation(operationType);

                assert operation != null;
                ComplexNumber result = operation.execute(numbers.get(i), numbers.get(i + 1));
                numbers.set(i, result);
                numbers.remove(i + 1);
                operators.remove(i);
            }

            else {
                i++;
            }
        }

        ComplexNumber finalResult = numbers.get(0);
        for (int i = 0; i < operators.size(); i++) {
            OperationType operationType = operators.get(i);
            Operation operation = OperationFactory.getOperation(operationType);
            assert operation != null;
            finalResult = operation.execute(finalResult, numbers.get(i + 1));
        }

        return finalResult;
    }
}