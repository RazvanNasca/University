package com.company;

public class Main {

    public static void main(String[] args) {

        ExpressionParser parser = new ExpressionParser();
        ComplexExpression exp = parser.ExpressionParser(args);
        if(exp == null)
            System.out.println("Expresie invalida!");
        else exp.execute();
    }
}

