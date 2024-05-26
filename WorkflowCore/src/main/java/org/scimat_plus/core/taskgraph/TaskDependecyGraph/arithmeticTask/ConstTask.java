package org.scimat_plus.core.taskgraph.TaskDependecyGraph.arithmeticTask;


import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.BasicTask;

/**
 * @author mjcobo
 */
public class ConstTask  implements ResultTask<Double> {

    private double c;

    public ConstTask(double c) {
        this.c = c;
    }

    @Override
    public Double getValue() {
        return c;
    }

    @Override
    public void run() {

        System.out.println("Const " + c);
        // do nothing
    }
}