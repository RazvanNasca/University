package com.company;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Cerc cerc = new Cerc(3, 0, 0);
        Patrat patrat = new Patrat(4);
        Area<Cerc> ArieCerc =  x -> Math.PI * Math.pow(x.getRaza(),2);
        //Area<Patrat> AriePatrat =  x -> x.getLatura() * x.getLatura();
        Area<Patrat> AriePatrat =  x -> Math.pow(x.getLatura(), 2);

        System.out.println(ArieCerc.getArea(cerc));
        System.out.println(AriePatrat.getArea(patrat));

        ArrayList<Cerc> ListaCercuri = new ArrayList<Cerc>();
        ListaCercuri.add(cerc);
        ListaCercuri.add(cerc);

        ArrayList<Patrat> ListaPatrate = new ArrayList<Patrat>();
        ListaPatrate.add(patrat);
        ListaPatrate.add(patrat);

        AreaHelper.printArie(ListaCercuri, ArieCerc);
        AreaHelper.printArie(ListaPatrate, AriePatrat);

        Consumer<Cerc> ConsumerCerc = System.out::println;  /// se poate pune orice functie
        ConsumerCerc.accept(cerc);

    }
}
