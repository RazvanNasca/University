package com.company.Model;

import com.company.Utils.Strategy;
import com.company.runner.DelayTaskRunner;
import com.company.runner.PrinterTaskRunner;
import com.company.runner.StrategyTaskRunner;

import java.time.LocalDateTime;

class TestRun {

    public static void main(String[] args) {
        MessageTask msg1 = new MessageTask("1", "Primul", "mesaj1", "Facultate1", "stud1", LocalDateTime.now());
        MessageTask msg2 = new MessageTask("2", "Al doilea", "mesaj2", "Facultate2", "stud2", LocalDateTime.now());
        MessageTask msg3 = new MessageTask("3", "Al treilea", "mesaj3", "Facultate3", "stud3", LocalDateTime.now());
       /// MessageTask[] mesaje = new MessageTask[]{msg1, msg2, msg3};


        StrategyTaskRunner runner = new StrategyTaskRunner(Strategy.valueOf(args[0]));
        runner.addTask(msg1);
        runner.addTask(msg2);
        runner.addTask(msg3);
        runner.executeAll();
        System.out.println('\n');

        PrinterTaskRunner decorator = new PrinterTaskRunner(runner);
        decorator.addTask(msg1);
        decorator.addTask(msg2);
        decorator.addTask(msg3);
        decorator.executeAll();
        System.out.println('\n');

        DelayTaskRunner delay = new DelayTaskRunner(runner);
        delay.addTask(msg1);
        delay.addTask(msg2);
        delay.addTask(msg3);
        delay.executeAll();
        System.out.println('\n');

        int [] numere = {4,2,5,7,1};
        SortingTask sortingTask = new SortingTask(numere, Strategy.valueOf(args[1]));
        runner.addTask(sortingTask);
        runner.executeAll();
        System.out.println(sortingTask.toString());

    }


}
