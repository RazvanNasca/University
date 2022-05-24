package com.company.runner;

import com.company.Utils.Strategy;
import com.company.Factory.TaskContainerFactory;
import com.company.Model.Task;
import com.company.container.Container;

public class StrategyTaskRunner implements TaskRunner
{
    private Container container;
    @Override
    public void executeOneTask()
    {
        if(!container.isEmpty())
            container.remove().execute();
    }

    @Override
    public void executeAll()
    {
        while(!container.isEmpty())
            executeOneTask();
    }

    @Override
    public void addTask(Task t)
    {
        container.add(t);
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
