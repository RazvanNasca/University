package com.company.runner;


import com.company.Model.Task;

public interface TaskRunner {
    void executeOneTask();
    void executeAll();
    void addTask(Task t);
    boolean hasTask();
}
