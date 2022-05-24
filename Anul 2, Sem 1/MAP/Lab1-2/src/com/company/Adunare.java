package com.company;

public class Adunare extends ComplexExpression{

    public Adunare(Operation op, NumarComplex[] sir)
    {
        super(op,sir);
    }

    @Override
    /*
        Creaza un nou obiect de tip NumarComplex care va fi compus din suma partilor fiecarui NumarComplex c1 si c2
    */
    protected NumarComplex executeOneOperation(NumarComplex c1, NumarComplex c2)
    {
        return new NumarComplex(c1.Adunare(c2).getReal(), c1.Adunare(c2).getImag());
    }

}
