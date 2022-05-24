package com.company.model;

public class BubbleSort extends SortingTask
{
    public BubbleSort(int[] numbers) { super(numbers); }

    @Override
    public void sort()
    {
        int i, j, temp;
        boolean swapped;
        int n = numbers.length;

        for (i = 0; i < n - 1; i++)
        {
            swapped = false;
            for (j = 0; j < n - i - 1; j++)
            {
                if (numbers[j] > numbers[j + 1])
                {
                    temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
    }
}
