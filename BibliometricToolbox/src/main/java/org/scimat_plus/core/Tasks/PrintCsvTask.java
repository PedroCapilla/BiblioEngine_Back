package org.scimat_plus.core.Tasks;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;

public class PrintCsvTask implements ResultTask<Dataset<Row>>{
    private Dataset<Row> csv;
    private final int nRows;

    public PrintCsvTask(String nRows){
        this.nRows = Integer.parseInt(nRows);
    }

    public void setCsv(Dataset<Row> csv){
        this.csv = csv;
    }

    @Override
    public void run(){
        System.out.println("Printing final DataSet....");
        System.out.println("Showing " + this.nRows + " rows: ");
        this.csv.show(this.nRows);
    }

    @Override
    public Dataset<Row> getValue() {
        return this.csv;
    }
}
