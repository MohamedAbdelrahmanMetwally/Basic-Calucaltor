package com.example.basiccalculator.Main.utils;

import java.util.Stack;

public class Calculator {
    public double evaluateExpression(String expression) {
        expression = expression.replaceAll("\\s", "");
        if (expression.isEmpty()) return 0;
        expression = expression.replaceAll("--", "+");
        expression = expression.replaceAll("\\+-", "-");
        expression = expression.replaceAll("-\\+", "-");
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        int i = 0;
        while (i < expression.length()) {
            char token = expression.charAt(i);
            if (token == '-' && (i == 0 || expression.charAt(i - 1) == '(')) {
                StringBuilder num = new StringBuilder("-");
                i++;
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i));
                    i++;
                }
                values.push(Double.parseDouble(num.toString()));
                continue;
            }
            if (Character.isDigit(token) || token == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i));
                    i++;
                }
                values.push(Double.parseDouble(num.toString()));
                continue;
            }
            if (token == '(') {
                operators.push(token);
            } else if (token == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            } else if ("+-*/".indexOf(token) != -1) {
                while (!operators.isEmpty() && precedence(token) <= precedence(operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(token);
            }
            i++;
        }
        while (!operators.isEmpty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }
    public int precedence(char op) {
        switch (op) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            default: return -1;
        }
    }
    public double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':{
                if (b == 0) return Double.NaN;
                return a / b;
            }
        }
        return 0;
    }
}