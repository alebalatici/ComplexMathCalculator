public class Main {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println("No arguments provided");
            return;
        }

        InputExpressionParser parser = new ComplexExpressionParser();
        ExpressionEvaluator evaluator = new ExpressionEvaluator(parser);

        if (parser.isValid(args)) {
            ComplexNumber result = evaluator.evaluate(args);
            System.out.println("Result: " + result.toString());
        }
        else {
            System.err.println("Invalid arguments provided");
        }
    }
}