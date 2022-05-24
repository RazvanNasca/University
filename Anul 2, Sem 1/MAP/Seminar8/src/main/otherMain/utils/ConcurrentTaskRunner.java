package map.seminar13.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentTaskRunner
{
    ReentrantLock locker = new ReentrantLock();
    private final Condition notEmpty = locker.newCondition();

    Thread[] workers;
    Queue<Runnable> itemQ = new LinkedList<>();

    public ConcurrentTaskRunner(int workerCount)
    {
        workers = new Thread[workerCount];
        // Create and start a separate thread for each worker
        for (int i = 0; i < workerCount; i++)
            (workers[i] = new Thread(this::Consume)).start();
    }

    /**
     * Producer: add an action to be added to the Queue
     * @param item - delegate method to be consumed
     */
    public void EnqueueItem(Runnable item)
    {
        locker.lock();
        try{
            itemQ.add(item);
            notEmpty.signalAll();
        } finally {
            locker.unlock();
        }
    }
    /**
     * Consume an action from the queue
     */
    void Consume()  {
        while (true)                        // Keep consuming until
        {                                   // told otherwise.
            Runnable item;
            locker.lock();
            try {
                while (itemQ.size() == 0) {
                    notEmpty.await();
                }
                item = itemQ.poll();
                if (item == null) return;
                item.run();

            } catch (InterruptedException e) {
                //e.printStackTrace();
            } finally {
                locker.unlock();
            }
        }
    }

    /**
     * Stop the process of task execution and wait till all already existing task
     * in the threadpool are done in case of waitForWorkers param is true
     * @param waitForWorkers
     * @throws InterruptedException
     */
    public void Shutdown(boolean waitForWorkers) throws InterruptedException {
        // Enqueue one null item per worker to make each exit.
        for (Thread worker: workers)
            EnqueueItem(null);

        // Wait for workers to finish
        if (waitForWorkers)
            for (Thread worker: workers)
                worker.join();
    }
}