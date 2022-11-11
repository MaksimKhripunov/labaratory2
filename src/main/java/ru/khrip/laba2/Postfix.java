package ru.khrip.laba2;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
@Component
public class Postfix {
    private double result;
    private Boolean isRes;
    private Boolean isCorrect;

    public Postfix() {
        this.result = 0;
        this.isRes=false;
        this.isCorrect=true;
    }
    public boolean isSpace(char c) {
        return c == ' ';
    }
    public boolean isOperator(char c) {
        if(c == '+' || c == '-' || c == '*' || c == '/')
            return true;
        return false;
    }
    public int priority(char symbol) {
        switch (symbol) {
            case '+', '-' : return 1;
            case '*', '/' : return  2;
            default : return  -1;
        }
    }
    public void processOperator(LinkedList<Double> numbers, char symbol) {
        double r = numbers.removeLast();
        double l = numbers.removeLast();
        switch (symbol) {
            case '+' : numbers.add(l + r);
            case '-' : numbers.add(l - r);
            case '*' : numbers.add(l * r);
            case '/' : numbers.add(l / r);
        }
    }

    public Boolean errors(String expression) {
        int left = 0;
        int right = 0;
        String str_2 = expression;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(')
                left++;
            if (expression.charAt(i) == ')')
                right++;
        }
        for (int i = 0; i < expression.length(); i++) {
            switch (expression.charAt(i)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '+', '-', '*', '/', '(', ')','.' :
                    str_2 = str_2.replace(str_2.charAt(i), ' ');
            }
        }
        str_2 = str_2.replace(" ", "");
        if(right == left && str_2.length() == 0)
            return true;
        return false;
    }

    public double eval(String expression) {
        LinkedList<Double> numbers = new LinkedList<Double>();
        LinkedList<Character> symbols = new LinkedList<Character>();
        if (errors(expression)) {
            isCorrect=true;
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                if (isSpace(c))
                    continue;
                if (c == '(')
                    symbols.add('(');
                else if (c == ')') {
                    while (symbols.getLast() != '(')
                        processOperator(numbers, symbols.removeLast());
                    symbols.removeLast();
                } else if (isOperator(c)) {
                    while (!symbols.isEmpty() && priority(symbols.getLast()) >= priority(c))
                        processOperator(numbers, symbols.removeLast());
                    symbols.add(c);
                } else {
                    StringBuilder operand = new StringBuilder();
                    while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i)=='.'))
                        operand.append(expression.charAt(i++));
                    --i;
                    numbers.add(Double.parseDouble(operand.toString()));
                }
            }
            while (!symbols.isEmpty())
                processOperator(numbers, symbols.removeLast());
            return numbers.get(0);
        }
        else isCorrect=false;
        return 0;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public Boolean getIsRes() {
        return isRes;
    }

    public void setIsRes(Boolean res) {
        isRes = res;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}