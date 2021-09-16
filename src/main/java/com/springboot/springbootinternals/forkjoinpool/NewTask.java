package com.springboot.springbootinternals.forkjoinpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class NewTask extends RecursiveAction {

    private long load = 0;

    public NewTask(Long load) {
        this.load = load;
    }

    @Override
    protected void compute() {

        ArrayList<NewTask> subTasks = new ArrayList<>(createSubtasks());

        for (RecursiveAction subtask : subTasks) {
            subtask.fork();
        }
    }

    private List<NewTask> createSubtasks() {
        ArrayList<NewTask> subtasks = new ArrayList<>();
        NewTask newTask1 = new NewTask(this.load / 2);
        NewTask newTask2 = new NewTask(this.load / 2);
        NewTask newTask3 = new NewTask(this.load / 2);

        subtasks.add(newTask1);
        subtasks.add(newTask2);
        subtasks.add(newTask3);
        return subtasks;
    }
}
