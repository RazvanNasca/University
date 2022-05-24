package com.company.runner;

import com.company.containter.Container;
import com.company.utils.Strategy;
import com.company.factory.TaskContainerFactory;
import com.company.model.Task;

public class StrategyTaskRunner implements TaskRunner
{
    private Container container;

    @Override
    public void executeOneTask()
    {
        if(!container.isEmpty())
        {
            this.container.remove().execute();
        }
    }

    @Override
    public void executeAll()
    {
        while(!this.container.isEmpty())
        {
            this.executeOneTask();
        }
    }

    @Override
    public void addTask(Task t)
    {
        this.container.add(t);
    }

    @Override
    public boolean hasTask()
    {
        return !this.container.isEmpty();
    }

    public StrategyTaskRunner(Strategy strategy)
    {
        this.container = TaskContainerFactory.getInstance().createContainer(strategy);
    }
}
