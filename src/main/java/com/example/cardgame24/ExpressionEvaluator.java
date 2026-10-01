package com.example.cardgame24;

/**
 * Evaluates arithmetic expressions with normal operator precedence and
 * parentheses using a recursive-descent parser.
 */
public class ExpressionEvaluator {

    private String expression;
    private int position;

    public double evaluate(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression is empty.");
        }

        this.expression = expression;
        position = 0;

        double result = parseExpression();
        skipWhitespace();

        if (position != expression.length()) {
            throw new IllegalArgumentException("Invalid expression.");
        }

        return result;
    }

    // Addition and subtraction have the lowest precedence.
    private double parseExpression() {
        double value = parseTerm();

        while (true) {
            skipWhitespace();

            if (match('+')) {
                value += parseTerm();
            } else if (match('-')) {
                value -= parseTerm();
            } else {
                return value;
            }
        }
    }

    // Multiplication and division are evaluated before addition and subtraction.
    private double parseTerm() {
        double value = parseFactor();

        while (true) {
            skipWhitespace();

            if (match('*')) {
                value *= parseFactor();
            } else if (match('/')) {
                double divisor = parseFactor();

                if (Math.abs(divisor) < 0.000000001) {
                    throw new ArithmeticException("Cannot divide by zero.");
                }

                value /= divisor;
            } else {
                return value;
            }
        }
    }

    // A factor is either a whole number or another expression inside parentheses.
    private double parseFactor() {
        skipWhitespace();

        if (match('(')) {
            double value = parseExpression();
            skipWhitespace();

            if (!match(')')) {
                throw new IllegalArgumentException("Missing closing parenthesis.");
            }

            return value;
        }

        return parseNumber();
    }

    private double parseNumber() {
        skipWhitespace();

        int start = position;

        while (position < expression.length()
                && Character.isDigit(expression.charAt(position))) {
            position++;
        }

        if (start == position) {
            throw new IllegalArgumentException("Expected a number.");
        }

        return Double.parseDouble(expression.substring(start, position));
    }

    private boolean match(char expected) {
        if (position < expression.length()
                && expression.charAt(position) == expected) {
            position++;
            return true;
        }

        return false;
    }

    private void skipWhitespace() {
        while (position < expression.length()
                && Character.isWhitespace(expression.charAt(position))) {
            position++;
        }
    }
}
