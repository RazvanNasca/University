package com.company.Factory;

import com.company.Model.AbstractSort;
import com.company.Model.BubbleSort;
import com.company.Model.MergeSort;
import com.company.Utils.Strategy;

public class SorterFactory {

    private static SorterFactory instance = null;
    private SorterFactory(){};

    public AbstractSort createSorter(Strategy strategy) {
        if(Strategy.BUBBLE_SORT == strategy)
            return new BubbleSort();
        else
            return new MergeSort();
    }

    public static SorterFactory getInstance(){
        if(instance == null)
            instance = new SorterFactory();
        return instance;
    }

}
