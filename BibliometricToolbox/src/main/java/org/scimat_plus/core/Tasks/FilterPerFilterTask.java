package org.scimat_plus.core.Tasks;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;
import static org.apache.spark.sql.functions.*;

import java.io.Serializable;

public class FilterPerFilterTask implements ResultTask<Dataset<Row>>, Serializable {
    private Dataset<Row> csv;
    private Dataset<Row> resultCsv;
    private String row1;
    private String row2;

    public FilterPerFilterTask(String row1, String row2){
        this.row1 = row1;
        this.row2 = row2;
    }

    @Override
    public Dataset<Row> getValue(){return this.resultCsv;}

    public void setCsv(Dataset<Row> csv){this.csv = csv;}

    public void splitRows(){
        if(this.csv.filter(col(this.row1).contains("//")).count() > 0){
            this.csv = this.csv.withColumn(this.row1, explode(split(col(this.row1), "//")));
        }
    }
    @Override
    public void run(){
        splitRows();
        this.resultCsv = this.csv
                .groupBy(this.row1)
                .agg(count(this.row2).alias(this.row2),
                        format_number(sum(col(row2)), 2).alias("Suma " + row2),
                        format_number(avg(col(row2)), 2).alias("Media " + row2),
                        format_number(stddev(col(row2)), 2).alias("DesviacionTipica " + row2),
                        count("Title").alias("Papers")

                )
                .orderBy(this.row1);

        this.resultCsv = this.resultCsv
                .filter(col(this.row1).isNotNull()
                .and(col(this.row2).isNotNull()));

    }
}
