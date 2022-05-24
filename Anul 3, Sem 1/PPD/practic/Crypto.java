package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Main {

    static class Tranzactie {
        private int cod;
        private int valoareTran;
        private int destinatar;
        private int idSupervizor;

        public Tranzactie(int cod, int valoareTran, int destinatar, int idSupervizor) {
            this.cod = cod;
            this.valoareTran = valoareTran;
            this.destinatar = destinatar;
            this.idSupervizor = idSupervizor;
        }

        @Override
        public String toString() {
            return "Tranzactie{" +
                    "cod=" + cod +
                    ", valoare tranzactie=" + valoareTran +
                    ", destinatar=" + destinatar +
                    ", supervizor=" + idSupervizor +
                    '}';
        }
    }


    static class Utilizator extends Thread{

        private BlockingQueue<Tranzactie> banda1;

        public Utilizator(BlockingQueue<Tranzactie> banda) {
            this.banda1 = banda;
        }

        public void Depune() {
            synchronized (banda1) {
                Random rand = new Random();
                int codCurent;

                synchronized (this){
                    codCurent = codUtilizator++;
                }

                int codDestinatar = rand.nextInt(100) + 1;
                while(codDestinatar == codCurent)
                    codDestinatar = rand.nextInt(100) + 1;

                int valoareTran = rand.nextInt(200) + 1;

                /// construim o tranzactie
                Tranzactie tranzactie = new Tranzactie(codCurent, valoareTran, codDestinatar, 1);

                /// o adaugam pe banda1 si notificam
                banda1.add(tranzactie);
                banda1.notifyAll();
                System.out.println("Tranzactia utilizatorului " + tranzactie.cod + " a fost initiata ");
            }
        }

        @Override
        public void run() {
            Depune();

            synchronized (this) {
                synchronized (banda1) {
                    utilizatoriDone++;
                    if (utilizatoriDone == N) {
                        banda1.notifyAll();
                    }
                }
            }
        }
    }

    static class Supervizor extends Thread {
        private BlockingQueue<Tranzactie> banda1;
        private BlockingQueue<Tranzactie> banda2;

        public Supervizor(BlockingQueue<Tranzactie> newBanda1, BlockingQueue<Tranzactie> newBanda2) {
            banda1 = newBanda1;
            banda2 = newBanda2;
        }

        public void Preia() {
            while (true) {
                synchronized (banda1) {
                    /// verificam sa nu fie banda1 goala
                    while (banda1.isEmpty() && utilizatoriDone < N) {
                        try {
                            banda1.wait();
                            ///  System.out.println(Thread.currentThread().getName()+" Consumer waiting....");
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    /// verificam daca banda1 e plina si nu mai avem utilizatori care sa astepte
                    if (banda1.isEmpty() && utilizatoriDone == N) {
                        break;
                    } else {
                        if (!banda1.isEmpty()) {
                            Tranzactie tranzactie = banda1.poll();
                            System.out.println("Tranzactia utilizatorului " + tranzactie.cod + " a fost preluata de catre supervizorul " + Thread.currentThread().getName());
                            if (tranzactie != null) {
                                synchronized (banda2) {
                                    /// TODO validari

                                    if(tranzactie.valoareTran < 100) {

                                        System.out.println("Tranzactia utilizatorului " + tranzactie.cod + " cu valoarea " + tranzactie.valoareTran + " catre destinatarul " + tranzactie.destinatar + " a fost ACCEPTATA de catre supervizorul " + Thread.currentThread().getName());
                                        Tranzactie transmitere = new Tranzactie(tranzactie.cod, tranzactie.valoareTran, tranzactie.destinatar, tranzactie.idSupervizor);

                                        /// adaugam tranzactia pe banda2 si notificam
                                        banda2.add(transmitere);
                                        banda2.notify();
                                        System.out.println("Tranzactia utilizatorului " + tranzactie.cod + " a fost transmisa la miner");
                                    }
                                    else
                                    {
                                        System.out.println("Tranzactia utilizatorului " + tranzactie.cod + " cu valoarea " + tranzactie.valoareTran + " catre destinatarul " + tranzactie.destinatar + " a fost RESPINSA de catre supervizorul " + Thread.currentThread().getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void run() {
            Preia();
            synchronized (this) {
                synchronized (banda2) {
                    supervizoriDone++;
                    if (supervizoriDone == M) {
                        banda2.notify();
                    }
                }
            }
        }
    }

    static class Miner extends Thread {
        private BlockingQueue<Tranzactie> banda2;

        public Miner(BlockingQueue<Tranzactie> banda2) {
            this.banda2 = banda2;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (banda2) {
                    /// verificam daca banda2 este goala si mai sunt supervizori care sa astepte, atunci punem banda2 in asteptare
                    while (banda2.isEmpty() && supervizoriDone < M) {
                        try {
                            banda2.wait();
//                        System.out.println(Thread.currentThread().getName()+" Consumer waiting....");
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    /// daca banda2 e goala si nu mai sunt supervizori, nu se intampla nimic
                    if (banda2.isEmpty() && supervizoriDone == M) {
                        return;
                    } else { /// altfel, salvam tranzactia
                        if (!banda2.isEmpty()) {
                            Tranzactie tranzactie = banda2.poll();
                            System.out.println("Minerul a salvat tranzactia initiata de utilizatorul " + tranzactie.cod + " cu valoarea " + tranzactie.valoareTran + " catre destinatarul " + tranzactie.destinatar + " , validata de supervizorul " + tranzactie.idSupervizor);
                        }
                    }
                }
            }
        }
    }


    private static BlockingQueue<Tranzactie> banda1;
    private static BlockingQueue<Tranzactie> banda2;
    private static int N = 50; // utilizatori
    private static int M = 4;  // supervizori
    protected static int utilizatoriDone = 0;
    protected static int supervizoriDone = 0;
    protected static int codUtilizator = 1;


    public static void main(String[] args) {

        banda1 = new LinkedBlockingQueue<>();
        banda2 = new LinkedBlockingQueue<>();

        List<Thread> utilizatori = new ArrayList<>();
        List<Thread> supervizori = new ArrayList<>();

        Utilizator utilizator = new Utilizator(banda1);
        Supervizor supervizor = new Supervizor(banda1, banda2);
        Miner miner = new Miner(banda2);


        /// cream thread-urile
        Thread minerWorker = new Thread(miner);

        for (int i = 0; i < N; i++) {
            Thread utilizatorWorker = new Thread(utilizator);
            utilizatori.add(utilizatorWorker);
        }

        for (int i = 0; i < M; i++) {
            Thread supervizorWorker = new Thread(supervizor);
            supervizori.add(supervizorWorker);
        }

        /// dam start la thread-uri
        minerWorker.start();
        utilizatori.forEach(Thread::start);
        supervizori.forEach(Thread::start);

        /// facem join la thread-uri
        utilizatori.forEach(th -> {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        supervizori.forEach(th -> {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            minerWorker.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

