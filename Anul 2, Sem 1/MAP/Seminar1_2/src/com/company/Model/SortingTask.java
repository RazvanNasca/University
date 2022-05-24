package com.company.Model;

import com.company.Factory.SorterFactory;
import com.company.Factory.TaskContainerFactory;
import com.company.Utils.Strategy;
import com.company.runner.StrategyTaskRunner;

public class SortingTask extends Task {

    protected int[] numere;
    protected AbstractSort sorter;

    public SortingTask(int[] numere, Strategy strategy)
    {
        this.numere = numere;
        this.sorter = SorterFactory.getInstance().createSorter(strategy);
    }

    @Override
    public void execute()
    {
        sorter.sort(this.numere);
    }

    @Override
    public String toString()
    {
        String s = "";
        for(int i = 0; i < this.numere.length - 1; i++)
            s += this.numere[i] + ", ";

        s += this.numere[this.numere.length - 1];

        return s;
    }

}
