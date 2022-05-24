import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Main {
    static class Nod {
        private long ID;
        private String tipOperatie;
        private int nrObiectePeBanda;

        public Nod(long ID, String tipOperatie, int nrObiectePeBanda) {
            this.ID = ID;
            this.tipOperatie = tipOperatie;
            this.nrObiectePeBanda = nrObiectePeBanda;
        }

        @Override
        public String toString() {
            return "Nod{" +
                    "ID=" + ID +
                    ", tipOperatie=" + tipOperatie +
                    ", nrObiectePeBanda=" + nrObiectePeBanda +
                    '}';
        }
    }

    static class Producer implements Runnable {
        private BlockingQueue<Integer> banda;
        private List<Nod> operatii;

        public Producer(BlockingQueue<Integer> newBanda, List<Nod> newOperatii) {
            banda = newBanda;
            operatii = newOperatii;
        }

        public boolean Pune(int i) {
            synchronized (banda) {
                // verificam sa nu fie banda prea plina
                while (banda.size()+4 > n && consumersDone<c) {
                    try {
                        banda.wait();
//                        System.out.println(Thread.currentThread().getName()+" Producer waiting....");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(banda.size() + 4 > n && consumersDone==c){
                    return true;
                } else{
                    if(banda.size() + 4 <= n){
                        for(int j=0; j<4; j++){
//                            System.out.println(Thread.currentThread().getName() + " Producer >>> producing >> " + i);
                            banda.add(i);
                            banda.notifyAll();
                            synchronized (operatii) {
                                operatii.add(new Nod(Thread.currentThread().getId(), "Pune", banda.size()));
                            }
                        }
                        return false;
                    }
                }
                return false;
            }
        }

        @Override
        public void run() {
            boolean added = false;
            for (int i = 0; i < 100; i++) {
                boolean result = Pune(i);
                if (result) {
                    synchronized (this) {
                        producersDone++;
                        added = true;
                    }
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(!added){
                synchronized (this) {
                    synchronized (banda) {
                        producersDone++;
                        if (producersDone == p) {
                            banda.notifyAll();
                        }
                    }
                }
            }
        }

    }


    static class Consumer extends Thread {
        private BlockingQueue<Integer> banda;
        private List<Nod> operatii;

        public Consumer(BlockingQueue<Integer> newBanda, List<Nod> newOperatii) {
            banda = newBanda;
            operatii = newOperatii;
        }

        public boolean Preia() {
            synchronized (banda) {
                // verificam sa nu fie banda prea goala
                while (banda.size()<3 && producersDone<p) {
                    try {
                        banda.wait();
//                        System.out.println(Thread.currentThread().getName()+" Consumer waiting....");
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if(banda.size()<3 && producersDone==p){
                    return true;
                } else {
                    if(banda.size()>=3){
                        for(int j=0; j<3; j++){
//                            System.out.println(Thread.currentThread().getName() + " Consumer >>>> consuming >>>" + goods.remove(0));
                            banda.poll();
                            banda.notifyAll();
                            synchronized (operatii) {
                                operatii.add(new Nod(currentThread().getId(), "Preia", banda.size()));
                            }
                        }
                        return false;
                    }
                }
                return false;
            }
        }

        @Override
        public void run() {
            boolean added = false;
            for (int i = 0; i < 100; i++) {
                boolean result = Preia();
                if(result){
                    synchronized (this){
                        consumersDone++;
                        added = true;
                    }
                    break;
                }

                try {
                    sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(!added){
                synchronized (this) {
                    synchronized (banda) {
                        consumersDone++;
                        if (consumersDone == c) {
                            banda.notifyAll();
                        }
                    }
                }
            }
        }

    }


    static class IteratorThread implements Runnable {
        private List<Nod> operatii;

        public IteratorThread(List<Nod> newOperatii) {
            operatii = newOperatii;
        }

        @Override
        public void run() {
            while (true) {

                synchronized (operatii){
                    operatii.forEach(System.out::println);
                }

                // check state not changed
                if (consumersDone == c && producersDone == p) {
                    return;
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static BlockingQueue<Integer> banda;
    private static List<Nod> operatii;
    private static int p = 3;
    private static int n = 20;
    private static int c = 2;
    protected static int producersDone = 0;
    protected static int consumersDone = 0;

    public static void main(String[] args) {
        banda = new LinkedBlockingQueue<>(n);
        operatii = new ArrayList<>();

        List<Thread> producers = new ArrayList<>();
        List<Thread> consumers = new ArrayList<>();

        Producer producer = new Producer(banda, operatii);
        Consumer consumer = new Consumer(banda, operatii);
        IteratorThread iteratorThread = new IteratorThread(operatii);

        Thread iteratorWorker = new Thread(iteratorThread);
        for (int i = 0; i < p; i++) {
            Thread producerWorker = new Thread(producer);
            producers.add(producerWorker);
        }

        for (int i = 0; i < c; i++) {
            Thread consumerWorker = new Thread(consumer);
            consumers.add(consumerWorker);
        }

        iteratorWorker.start();
        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        producers.forEach(pr -> {
            try {
                pr.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        consumers.forEach(cn -> {
            try {
                cn.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            iteratorWorker.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
