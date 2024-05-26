package org.scimat_plus.core.Tasks;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.Serializable;

public class BetweenFilterTask implements ResultTask<Dataset<Row>>, Serializable {
    private Dataset<Row> csv;
    private Dataset<Row> resultCsv;
    private String row;
    private Integer initialYear;
    private Integer finalYear;

    public BetweenFilterTask(String row, String initialYear, String finalYear){
        this.row = row;
        this.initialYear = Integer.parseInt(initialYear);
        this.finalYear = Integer.parseInt(finalYear);
    }

    @Override
    public Dataset<Row> getValue(){return this.resultCsv;}

    public void setCsv(Dataset<Row> csv){this.csv = csv;}

    @Override
    public void run(){
        this.resultCsv = this.csv.filter(this.csv.col(this.row).between(this.initialYear, this.finalYear));
    }
}
