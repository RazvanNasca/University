package com.company.runner;

import com.company.Utils.Constants;

import java.time.LocalDateTime;

public class PrinterTaskRunner extends AbstractTaskRunner{

    public PrinterTaskRunner(TaskRunner runner)
    {
        super(runner);
    }

    @Override
    public void executeOneTask()
    {
        runner.executeOneTask();
        decorateExecuteOneTask();
    }

    private void decorateExecuteOneTask()
    {
        System.out.println("Task executat:\n" + LocalDateTime.now().format(Constants.DATE_TIME_FORMATER));
    }


}
