package com.company;

public class ExpressionFactory {

    private static ExpressionFactory instance = null;
    private ExpressionFactory() {};

    public static ExpressionFactory getInstance()
    {
        if(instance == null)
            instance = new ExpressionFactory();
        return instance;
    }

    public ComplexExpression createExpression(Operation operation, NumarComplex[] args)
    {
        switch(operation)
        {
            case ADDITION: return new Adunare(operation, args);
            case SUBTRACTION: return new Scadere(operation, args);
            case MULTIPLICATION: return new Inmultire(operation, args);
            case DIVISION: return new Impartire(operation, args);
        }
        return null;
    }

}
