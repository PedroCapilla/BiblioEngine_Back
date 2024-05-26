package org.scimat_plus.core.taskgraph.TaskDependecyGraph.arithmeticTask;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;

public class MultTask implements ResultTask<Double> {
    private double a;
    private double b;
    private double result;
    public MultTask(){}

    @Override
    public Double getValue(){return this.result;}

    @Override
    public String toString() {
        return "MultTask{" +
                "a=" + a +
                ", b=" + b +
                ", result=" + result +
                '}';
    }

    public void setA(double a){
        this.a = a;
    }
    public void setB(double b){
        this.b = b;
    }
    @Override
    public void run(){
        this.result = a * b;
        System.out.println("Mult " +a+ " * "+b+" = " + this.result);
    }
}
