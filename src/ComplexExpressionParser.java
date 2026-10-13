public class ComplexExpressionParser implements InputExpressionParser {
    @Override
    public boolean isValid(String[] args) {
        if (args == null || args.length < 3 || args.length % 2 == 0) {
            return false;
        }

        for (int i = 0; i < args.length; i ++) {
            if (i % 2 == 0) {
                if (parseComplex(args[i]) == null) {
                    return false;
                }
            }
            else {
                try {
                    OperationType.fromSymbol(args[i]);
                }
                catch (IllegalArgumentException e) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ComplexNumber parseComplex(String str) {
        if (str == null) {
            return null;
        }

        str = str.replace(" ", "");
        if (str.isEmpty()) {
            return null;
        }

        try {
            double re = 0;
            double im = 0;

            if (str.charAt(str.length() - 1) == 'i') { // exista parte imaginara
                String content = str.substring(0, str.length() - 1); // elimina ultimul i

                int signIndex = Math.max(content.lastIndexOf('+'), content.lastIndexOf('-'));

                if (signIndex > 0) { // daca exista parte reala
                    String reStr = content.substring(0, signIndex);
                    re = Double.parseDouble(reStr);

                    String imStr = content.substring(signIndex);
                    im = parseImaginaryPart(imStr);
                }

                else {
                    im = parseImaginaryPart(content);
                }
            }

            else {
                re = Double.parseDouble(str);
            }

            return new ComplexNumber(re, im);
        }
        catch (Exception e) {
            return null;
        }
    }

    private double parseImaginaryPart(String imStr) {
        imStr = imStr.replace("*", "");
        if (imStr.isEmpty() || imStr.equals("+")) {
            return 1.0;
        }

        if (imStr.equals("-")) {
            return -1.0;
        }

        return Double.parseDouble(imStr);
    }
}