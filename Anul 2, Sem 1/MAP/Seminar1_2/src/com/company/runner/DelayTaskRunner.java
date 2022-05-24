package com.company.runner;

import com.company.Model.Task;
import com.company.Utils.Constants;

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
                this.executeOneTask();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void decorateExecuteOneTask()
    {
        System.out.println("Task executat: " + LocalDateTime.now().format(Constants.DATE_TIME_FORMATER));
    }
}
