package com.company;

public abstract class ComplexExpression {

    private Operation operatie;
    private NumarComplex[] args;

    public ComplexExpression(Operation op, NumarComplex[] sir)
    {
        this.operatie = op;
        this.args = sir;
    }

    protected abstract NumarComplex executeOneOperation(NumarComplex c1, NumarComplex c2);

    public final void execute()
    {
        NumarComplex nr = this.args[0];
        for(int i = 1; i < this.args.length; i++)
            nr = this.executeOneOperation(nr, this.args[i]);
        System.out.println(nr);
    }

}
