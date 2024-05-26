package org.scimat_plus.core.taskgraph.TaskDependecyGraph.arithmeticTask;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.BasicTask;

/**
 * @author mjcobo
 */
public class PrintNumberTask implements BasicTask {

    private double n;

    public PrintNumberTask() {
    }

    public void setN(double n) {
        this.n = n;
    }

    @Override
    public void run() {

        System.out.println("The result is: " + n);
    }

}