package com.company.container;

import com.company.Utils.Constants;
import com.company.Model.Task;

public class StackContainer extends AbstractContainer
{
    public StackContainer()
    {
        super();
    }

    @Override
    public Task remove()
    {
        if(!this.isEmpty())
        {
            this.size--;
            return this.tasks[this.size];
        }
        return null;
    }
}
