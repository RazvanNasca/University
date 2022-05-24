package com.company.Factory;

import com.company.Utils.Strategy;
import com.company.container.Container;

public interface Factory {
    Container createContainer (Strategy strategy);
}
