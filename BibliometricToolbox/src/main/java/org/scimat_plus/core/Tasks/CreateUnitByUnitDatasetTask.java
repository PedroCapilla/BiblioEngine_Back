package org.scimat_plus.core.Tasks;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;


import java.io.Serializable;
public class CreateUnitByUnitDatasetTask implements  ResultTask<Dataset<Row>>, Serializable{
    private Dataset<Row> csv;
    private String firstRow;
    private String secondRow;
    private Dataset<Row> resultCsv;
    public CreateUnitByUnitDatasetTask(String firstRow, String secondRow){
        this.firstRow = firstRow;
        this.secondRow = secondRow;
    }

    @Override
    public Dataset<Row> getValue(){return this.resultCsv;}

    public void setCsv(Dataset<Row> csv){this.csv = csv;}
    public void run(){
        this.csv = this.csv.select(this.firstRow, this.secondRow);
        this.resultCsv = this.csv.groupBy(this.firstRow)
                .agg(org.apache.spark.sql.functions.collect_list(this.secondRow).alias(this.secondRow));
        this.resultCsv.orderBy(this.firstRow);
    }
}
