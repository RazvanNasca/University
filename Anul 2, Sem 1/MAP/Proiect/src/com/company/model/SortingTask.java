package com.company.model;

public abstract class SortingTask extends Task
{
    protected int[] numbers;

    protected SortingTask(int[] numbers) { this.numbers = numbers; }

    public abstract void sort();

    @Override
    public void execute()
    {

    }
}