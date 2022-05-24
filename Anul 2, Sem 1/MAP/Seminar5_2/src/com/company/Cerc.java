package com.company;

public class Cerc {

    private double raza;
    /// coordonate centru cerc
    private double x;
    private double y;

    public Cerc(double raza, double x, double y)
    {
        this.raza = raza;
        this.x = x;
        this.y = y;
    }

    public double getRaza()
    {
        return raza;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public String toString()
    {
        return "Cerc: " + getRaza();
    }

}
