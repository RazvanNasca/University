package map.seminar13.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by camelia on 1/15/2018.
 */
public class ConcurrentTaskRunner_Executor {
    ReentrantLock locker = new ReentrantLock();
    private final Condition notEmpty = locker.newCondition();
    int workerCount=0;

    ExecutorService workers;
    Queue<Runnable> itemQ = new LinkedList<>();

    public ConcurrentTaskRunner_Executor(int workerCount)
    {
        this.workerCount=workerCount;
        workers = Executors.newFixedThreadPool(workerCount);
        // Create and start a separate thread for each worker
        for (int i = 0; i < workerCount; i++)
            workers.execute(this::Consume);
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
        for (int i=0; i<workerCount; i++)
            EnqueueItem(null);

        // Wait for workers to finish
        if (waitForWorkers)
            workers.shutdown();
    }
}
