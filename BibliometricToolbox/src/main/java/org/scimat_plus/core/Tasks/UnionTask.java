package org.scimat_plus.core.Tasks;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;

public class UnionTask implements ResultTask<Dataset<Row>> {

    private Dataset<Row> csv1;
    private Dataset<Row> csv2;

    private Dataset<Row> resultCsv;

    public UnionTask(){}

    @Override
    public Dataset<Row> getValue(){ return this.resultCsv;}

    public void setCsv1(Dataset<Row> csv1){this.csv1 = csv1;}
    public void setCsv2(Dataset<Row> csv2){this.csv2 = csv2;}

    @Override
    public void run(){
        this.resultCsv = this.csv1.unionByName(this.csv2);
        System.out.println("Joining " + this.csv1.count() + " rows with " + this.csv2.count() +
                " rows in a new dataset with " + this.resultCsv.count() + " rows"
                );

        System.out.println("----------------------csv1---------------------------");
        this.csv1.show();
        System.out.println("----------------------csv2---------------------------");
        this.csv2.show();
    }
}
