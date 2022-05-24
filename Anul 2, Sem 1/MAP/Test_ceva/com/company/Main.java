package com.company;

class NumarComplex{

    /// Campuri
    private double real, imag;

    /// Constructor
    public NumarComplex(){real = 0; imag = 0;}
    public NumarComplex(double r, double i){real = r; imag = i;}

    /// Getters and setters
    public double getReal(){return real;}
    public double getImag(){return imag;}
    public void setReal(double nr){real = nr;}
    public void setImag(double nr){imag = nr;}

    /// Metode

    public NumarComplex Conjugatul()
    {
        return new NumarComplex(real, (-1)*imag);
    }

    public NumarComplex Adunare(NumarComplex nr)
    {
        return new NumarComplex(nr.real + real, nr.imag + imag);
    }

    public NumarComplex Scadere(NumarComplex nr)
    {
        return new NumarComplex(nr.real - real, nr.imag - imag);
    }

    public NumarComplex Inmultire(NumarComplex nr)
    {
        return new NumarComplex(real * nr.real - imag * nr.imag, real * nr.imag + imag * nr.real);
    }

    public NumarComplex Impartire(NumarComplex nr)
    {
        return new NumarComplex(this.Inmultire(nr.Conjugatul()).real / (Math.pow(nr.real,2) + Math.pow(nr.imag,2)), this.Inmultire(nr.Conjugatul()).imag / (Math.pow(nr.real,2) + Math.pow(nr.imag,2)));
    }


}

public class Main {

    public static void main(String[] args) {

        /// 3 + 2i
        NumarComplex nr1 = new NumarComplex();
        nr1.setImag(2);
        nr1.setReal(3);

        /// 2 + i
        NumarComplex nr2 = new NumarComplex(2, 1);

        NumarComplex numar = nr1.Impartire(nr1);
        System.out.println(numar.getReal() + " + " + numar.getImag() + "i");

    }
}
