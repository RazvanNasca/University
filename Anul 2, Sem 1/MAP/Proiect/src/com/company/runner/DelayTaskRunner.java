package com.company.runner;

import com.company.model.Task;
import com.company.utils.Constanta;

import java.time.LocalDateTime;

public class DelayTaskRunner extends AbstractTaskRunner
{
    public DelayTaskRunner(TaskRunner runner) { super(runner); }

    @Override
    public void executeOneTask()
    {
        runner.executeOneTask();
        this.decorateExecuteOneTask();
    }

    @Override
    public void executeAll()
    {
        while(runner.hasTask())
        {
            try
            {
                Thread.sleep(3000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            this.executeOneTask();
        }
    }

    private void decorateExecuteOneTask()
    {
        System.out.println("Task executat: " + LocalDateTime.now().format(Constanta.DATE_TIME_FORMATTER));
    }
}
