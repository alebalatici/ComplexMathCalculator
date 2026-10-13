public class OperationFactory {
    public static Operation getOperation(OperationType operationType) {
        return switch (operationType) {
            case ADD -> new Addition();
            case SUBTRACT -> new Subtraction();
            case MULTIPLY -> new Multiplication();
            case DIVIDE -> new Division();
            default -> null;
        };
    }
}