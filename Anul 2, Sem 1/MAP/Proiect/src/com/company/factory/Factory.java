package com.company.factory;

import com.company.containter.Container;
import com.company.utils.Strategy;

public interface Factory
{
    Container createContainer(Strategy strategy);
}
