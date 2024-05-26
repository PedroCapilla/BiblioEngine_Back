package org.scimat_plus.core.Tasks;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;

import java.io.Serializable;

public class PrintIntegerTask implements ResultTask<Double>, Serializable {
    private Double result;
    private String task;

    public PrintIntegerTask(String task){
        this.task = task;
    }

    public void setResult(Double result){this.result = result;}

    @Override
    public Double getValue(){
        return this.result;
    }

    @Override
    public void run(){
        System.out.println(this.task + " del dataset estudiado es: " + this.result);
    }
}
