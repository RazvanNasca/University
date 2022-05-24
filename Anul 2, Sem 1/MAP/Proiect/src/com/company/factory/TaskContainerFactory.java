package com.company.factory;

import com.company.containter.Container;
import com.company.containter.QueueContainer;
import com.company.containter.StackContainer;
import com.company.utils.Strategy;

public class TaskContainerFactory implements Factory
{
    private static TaskContainerFactory instance = null;

    private TaskContainerFactory() {}

    @Override
    public Container createContainer(Strategy strategy)
    {
        if(strategy == Strategy.LIFO)
            return new StackContainer();

        if(strategy == Strategy.FIFO)
            return new QueueContainer();

        return null;
    }

    public static TaskContainerFactory getInstance()
    {
        if(instance == null)
        {
            instance = new TaskContainerFactory();
        }
        return instance;
    }
}
