package com.company;

public class Scadere extends ComplexExpression{

    NumarComplex[] nums;

    public Scadere(Operation op, NumarComplex[] sir)
    {
        super(op, sir);
    }

    @Override
    /*
        Creaza un nou obiect de tip NumarComplex care va fi compus din diferenta partilor fiecarui NumarComplex c1 si c2
    */
    public NumarComplex executeOneOperation(NumarComplex c1, NumarComplex c2)
    {
        return new NumarComplex(c1.Scadere(c2).getReal(), c1.Scadere(c2).getImag());
    }

}
