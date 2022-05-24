package com.company;

class NumarComplex{

    /// Campuri
    private double real, imag;

    /// Constructor
    ///public NumarComplex(){real = 0; imag = 0;}
    public NumarComplex(double r, double i){real = r; imag = i;}

    /// Getters and setters
    public double getReal(){return real;}
    public double getImag(){return imag;}
    public void setReal(double nr){real = nr;}
    public void setImag(double nr){imag = nr;}

    /// Metode

    /// returneaza conjugatul unui numar complex si returneaza un NumarComplex
    public NumarComplex Conjugatul()
    {
        return new NumarComplex(real, (-1)*imag);
    }

    /// aduna 2 numere complexe si returneaza un NumarComplex
    public NumarComplex Adunare(NumarComplex nr)
    {
        return new NumarComplex(nr.real + real, nr.imag + imag);
    }

    /// scade 2 numere complexe si returneaza un NumarComplex
    public NumarComplex Scadere(NumarComplex nr)
    {
        return new NumarComplex(nr.real - real, nr.imag - imag);
    }

    /// inmulteste 2 numere complexe si returneaza un NumarComplex
    public NumarComplex Inmultire(NumarComplex nr)
    {
        return new NumarComplex(real * nr.real - imag * nr.imag, real * nr.imag + imag * nr.real);
    }

    /// imparte un numar complex la altul si returneaza un NumarComplex
    public NumarComplex Impartire(NumarComplex nr)
    {
        return new NumarComplex(this.Inmultire(nr.Conjugatul()).real / (Math.pow(nr.real,2) + Math.pow(nr.imag,2)), this.Inmultire(nr.Conjugatul()).imag / (Math.pow(nr.real,2) + Math.pow(nr.imag,2)));
    }

    @Override
    /// afiseaza numarul ca string
    public String toString()
    {
        if(this.real == 0)
            return this.imag + "i";
        if(this.imag == 0)
            return "" + this.real;
        if(this.imag < 0)
            return this.real + " - " + this.imag + "*i";
        else
            return this.real + " + " + this.imag + "*i";
    }

}
