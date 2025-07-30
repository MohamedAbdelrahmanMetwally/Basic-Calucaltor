package com.example.basiccalculator;

import java.util.Stack;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.txtView);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @SuppressLint("NonConstantResourceId")
    public void action(View v) {
        int id = v.getId();
        String currentText = textView.getText().toString();

        if (id == R.id.btn0) textView.append("0");
        else if (id == R.id.btn1) textView.append("1");
        else if (id == R.id.btn2) textView.append("2");
        else if (id == R.id.btn3) textView.append("3");
        else if (id == R.id.btn4) textView.append("4");
        else if (id == R.id.btn5) textView.append("5");
        else if (id == R.id.btn6) textView.append("6");
        else if (id == R.id.btn7) textView.append("7");
        else if (id == R.id.btn8) textView.append("8");
        else if (id == R.id.btn9) textView.append("9");
        else if (id == R.id.btnPlus) textView.append(" + ");
        else if (id == R.id.btnMuliply) textView.append(" * ");
        else if (id == R.id.btnDivision) textView.append(" / ");
        else if (id == R.id.btnMinus) {
            if (currentText.isEmpty() || currentText.endsWith("(")) {
                textView.append("-");
            } else if ("+-*/".contains(currentText.substring(currentText.length() - 1))) {
                if (!currentText.endsWith("-")) {
                    textView.append("-");
                }
            } else {
                textView.append(" - ");
            }
        }
        else if (id == R.id.btnEqual) {
            Calculator c = new Calculator();
            if (!currentText.isEmpty()) {
                textView.setText("" + c.evaluateExpression(currentText));
            }
        }
        else if (id == R.id.btnBack) {
            if (!currentText.isEmpty()) {
                textView.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
    }
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
                case '/': return a / b;
            }
            return 0;
        }
    }
}