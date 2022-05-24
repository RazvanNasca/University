package com.company;

public class Impartire extends ComplexExpression{

    public Impartire(Operation op, NumarComplex[] sir)
    {
        super(op, sir);
    }

    @Override
    /*
        Creaza un nou obiect de tip NumarComplex care va fi compus din impartirea partilor fiecarui NumarComplex c1 si c2
    */
    public NumarComplex executeOneOperation(NumarComplex c1, NumarComplex c2)
    {
        return new NumarComplex(c1.Impartire(c2).getReal(), c1.Impartire(c2).getImag());
    }

}
