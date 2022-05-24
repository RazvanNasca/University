package utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentTaskRunner_Schelet {

    Lock locker = new ReentrantLock();
    private final Condition notEmpty = locker.newCondition();

    Thread[] workers;
    Queue<Runnable> itemQ = new LinkedList<>();

    public ConcurrentTaskRunner_Schelet(int workerCount)
    {
        //creates workerCount workers


        // Create and start a separate thread for each worker via metoda Consume

    }

    /**
     * Producer: add an action to be added to the Queue
     * @param item - delegate method to be consumed
     */
    public void EnqueueItem(Runnable item)
    {

    }
    /**
     * Consume an action from the queue
     */
    void Consume()  {
        while (true)   // Keep consuming until told otherwise.
        {

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


        // Wait for workers to finish if waitForWorkers is true

    }


}
