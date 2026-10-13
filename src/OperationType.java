public enum OperationType {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private final String symbol;

    OperationType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static OperationType fromSymbol(String symbol) {
        for (OperationType operationType : values()) {
            if (operationType.getSymbol().equals(symbol)) {
                return operationType;
            }
        }

        throw new IllegalArgumentException("Unknown symbol: " + symbol);
    }
}