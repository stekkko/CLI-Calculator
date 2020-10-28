import java.io.IOException;

public class ConsoleCalculator {
    private static final String VALID = "0123456789+-*/.^()";


    public static void main(String[] args) throws IOException {
        InputScanner scanner = new InputScanner();

        System.out.println("Welcome to CLI Calculator!");
        System.out.println("Symbols you can use to construct an arithmetic expression:");
        System.out.println("0123456789+-*/()^.");
        System.out.println("Type exit to close program");

        /**
         * main workflow
         */
        while (true) {
            String expr = scanner.nextLine();

            if (Processor.removeAllInvalidSymbols(expr, "exit").equals("exit"))
                break;

            if (Processor.removeAllInvalidSymbols(expr, "easter").equals("easter")) {
                System.out.println("\\(◕‿◕)/ YOU HAVE FOUND EASTER EGG. CONTINUE WORKING.");
                continue;
            }

            try {
                expr = Processor.removeAllInvalidSymbols(expr, VALID);
                double result = Processor.eval(Processor.buildExpression(expr));
                System.out.println("Result: " + result);
            } catch (WrongArithmeticException e) {
                System.out.println("Wrong arithmetic expression!");
            }
        }
    }
}
