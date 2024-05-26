package org.scimat_plus.core.Tasks;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;

import java.io.Serializable;

public class GreaterThanTask implements ResultTask<Dataset<Row>>, Serializable {
    private Dataset<Row> csv;
    private String row;
    private Integer number;
    private Dataset<Row> resultCsv;

    public GreaterThanTask(String row, String number){
        this.row = row;
        this.number = Integer.parseInt(number);
    }

    @Override
    public Dataset<Row> getValue(){return this.resultCsv;}
    public void setCsv(Dataset<Row> csv){this.csv = csv;}
    @Override
    public void run(){
        this.resultCsv = this.csv.filter(this.csv.col(this.row).gt(this.number));
    }
}
