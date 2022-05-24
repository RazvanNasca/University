package com.company.model;

import com.company.utils.Strategy;
import com.company.runner.DelayTaskRunner;
import com.company.runner.StrategyTaskRunner;

import java.time.LocalDateTime;

public class TestRun
{
    public static void main(String[] args)
    {
        MessageTask m1 = new MessageTask("1", "Feedback", "Ai obtinut nota 9.68", "Profesor ASC", "me", LocalDateTime.now());
        MessageTask m2 = new MessageTask("2", "Raport", "Ma doare burta :(", "eu", "mine", LocalDateTime.now());
        MessageTask[] msgs = new MessageTask[]{ m1, m2 };

        /*
        if(args[0] == "LIFO")
        {
            StrategyTaskRunner runner = new StrategyTaskRunner(Strategy.LIFO);
        }
        else
        {
            StrategyTaskRunner runner = new StrategyTaskRunner(Strategy.FIFO);
        }
        */
        StrategyTaskRunner runner = new StrategyTaskRunner(Strategy.FIFO);
        runner.addTask(msgs[0]);
        runner.addTask(msgs[1]);
        //runner.executeAll();
        //PrinterTaskRunner decorator = new PrinterTaskRunner(runner);
        //decorator.executeAll();
        DelayTaskRunner delay = new DelayTaskRunner(runner);
        delay.executeAll();
    }
}
