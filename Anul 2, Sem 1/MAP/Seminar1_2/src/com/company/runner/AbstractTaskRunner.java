package com.company.runner;

import com.company.Model.Task;

import static java.lang.Thread.sleep;

public abstract class AbstractTaskRunner implements TaskRunner{

    protected TaskRunner runner;

    public AbstractTaskRunner(TaskRunner runner)
    {
        this.runner = runner;
    }

    @Override
    public abstract void executeOneTask();

    @Override
    public void executeAll()
    {
        while(runner.hasTask())
            executeOneTask();
    }

    @Override
    public void addTask(Task t)
    {
        runner.addTask(t);
    }

    @Override
    public boolean hasTask()
    {
        return this.runner.hasTask();
    }

}
