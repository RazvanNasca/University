package com.company;

public class Inmultire extends ComplexExpression{

    NumarComplex[] nums;

    public Inmultire(Operation op, NumarComplex[] sir)
    {
        super(op, sir);
    }

    @Override
    /*
        Creaza un nou obiect de tip NumarComplex care va fi compus din inmultirea partilor fiecarui NumarComplex c1 si c2
    */
    public NumarComplex executeOneOperation(NumarComplex c1, NumarComplex c2)
    {
        return new NumarComplex(c1.Inmultire(c2).getReal(), c2.Inmultire(c2).getImag());
    }

}
