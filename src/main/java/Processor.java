import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class Processor {

    /**
     * This function will remove inVALID symbols witch are differend from @param valid
     * @param str iput string
     * @param valid symbols is allowed
     * @return changed format string from input str
     */
    static String removeAllInvalidSymbols(String str, String valid) {
        if (str == null || valid == null) return "";

        StringBuilder result = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (valid.indexOf(c) >= 0) {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * Builds arithmetic expression from giving string
     * @param str must be with no Invalid Symbols
     * @return List of values in Reverse Polsk Notation
     * @throws WrongArithmeticException if there is Invalic symbols
     */
    static List<Object> buildExpression(String str) throws WrongArithmeticException {
        if (str == null) return new ArrayList<>();

        List<Object> result = new ArrayList<>();
        Stack<Character> stack = new Stack<>();

        StringBuilder buildNumber = new StringBuilder();
        boolean havePoint = false;
        for (char c : str.toCharArray()) {
            if ('0' <= c && c <= '9') {
                buildNumber.append(c);
            } else if (c == '.') {
                if (havePoint) {
                    throw new WrongArithmeticException();
                } else {
                    havePoint = true;
                    buildNumber.append(c);
                }
            } else {
                if (buildNumber.length() > 0) {
                    double val = Double.parseDouble(buildNumber.toString());
                    result.add(val);
                    havePoint = false;
                    buildNumber = new StringBuilder();
                }

                if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    boolean correct = false;
                    while (!stack.isEmpty()) {
                        if (stack.peek().equals('(')) {
                            stack.pop();
                            correct = true;
                            break;
                        }
                        result.add(stack.pop());
                    }
                    if (!correct)
                        throw new WrongArithmeticException();
                } else if (c == '*' || c == '/') {
                    while (!stack.isEmpty()) {
                        if (stack.peek().equals('*') || stack.peek().equals('/') || stack.peek().equals('^')) {
                            result.add(stack.pop());
                        } else break;
                    }
                    stack.push(c);
                } else if (c == '+' || c == '-') {
                    while (!stack.isEmpty()) {
                        char peek = stack.peek();
                        if (peek == '+' || peek == '-' || peek == '*' || peek == '/' || peek == '^') {
                            result.add(stack.pop());
                        } else break;
                    }
                    stack.push(c);
                } else if (c == '^') {
                    while (!stack.isEmpty()) {
                        if (stack.peek().equals('^')) {
                            result.add(stack.pop());
                        } else break;
                    }
                    stack.push(c);
                } else throw new WrongArithmeticException("Symbol" + c);
            }
        }

        if (buildNumber.length() > 0) {
            double val = Double.parseDouble(buildNumber.toString());
            result.add(val);
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    /**
     * Evaluate from reversePorlsNotation to some Double value
     * @param reversePolskNotation check wiki for Reverse Polsk Notation
     * @return double result value
     * @throws WrongArithmeticException if giving param contains number and operands in wrong order
     */
    static double eval(List<Object> reversePolskNotation) throws WrongArithmeticException{
        if (reversePolskNotation == null) return 0;

        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < reversePolskNotation.size(); i++) {
            Object curr = reversePolskNotation.get(i);

            if (curr instanceof Double) {
                stack.push((Double) curr);
            }
            else {
                if (curr.equals('+')) {
                    stack.push(stack.pop() + stack.pop());
                } else if (curr.equals('-')) {
                    stack.push(-stack.pop() + stack.pop());
                } else if (curr.equals('*')) {
                    stack.push(stack.pop() * stack.pop());
                } else if (curr.equals('/')) {
                    double p = stack.pop();
                    stack.push(stack.pop() / p);
                } else if (curr.equals('^')) {
                    double r = stack.pop();
                    stack.push(Math.pow(stack.pop(), r));
                } else {
                    throw new WrongArithmeticException();
                }
            }
        }
        if (stack.size() != 1)
            throw new WrongArithmeticException();
        return stack.pop();
    }

}
