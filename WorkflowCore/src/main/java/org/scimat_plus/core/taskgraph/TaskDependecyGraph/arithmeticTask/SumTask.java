package org.scimat_plus.core.taskgraph.TaskDependecyGraph.arithmeticTask;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;

/**
 * @author mjcobo
 */
public class SumTask implements ResultTask<Double> {

    private double a;
    private double b;
    private double result;

    public SumTask() {

    }

    @Override
    public Double getValue() {
        return this.result;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    @Override
    public void run() {

        this.result = a + b;

        System.out.println("Sum " + a + " + " + b + " = "  + this.result);
    }
}
