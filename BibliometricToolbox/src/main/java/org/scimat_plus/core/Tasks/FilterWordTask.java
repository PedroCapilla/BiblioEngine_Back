package org.scimat_plus.core.Tasks;

import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import static org.apache.spark.sql.functions.*;

import java.io.Serializable;
import java.util.Objects;

public class FilterWordTask implements ResultTask<Dataset<Row>>, Serializable {
    private Dataset<Row> csv;
    private String row;
    private String keyword;
    private Dataset<Row> resultCsv;

    public FilterWordTask(String row, String keyword){
        this.row = row;
        this.keyword = keyword;
    }

    @Override
    public Dataset<Row> getValue(){return this.resultCsv;}

    public void setCsv(Dataset<Row> csv){this.csv = csv;}

    @Override
    public void run(){

        this.resultCsv = this.csv.filter(this.csv.col(this.row).contains(this.keyword));

    }
}
