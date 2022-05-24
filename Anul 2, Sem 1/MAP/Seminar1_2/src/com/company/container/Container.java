package com.company.container;

import com.company.Model.Task;

public interface Container {
    Task remove();

    void add(Task task);

    int size();

    boolean isEmpty();
}
