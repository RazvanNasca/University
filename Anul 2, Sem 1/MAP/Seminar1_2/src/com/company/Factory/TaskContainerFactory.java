package com.company.Factory;

import com.company.Utils.Strategy;
import com.company.container.Container;
import com.company.container.QueueContainer;
import com.company.container.StackContainer;


public class TaskContainerFactory implements Factory{

    private static TaskContainerFactory instance = null;

    private TaskContainerFactory(){}

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
            instance = new TaskContainerFactory();
        return instance;
    }

}
