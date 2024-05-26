package org.scimat_plus.core.Tasks;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;

import static org.apache.spark.sql.functions.*;

import java.io.Serializable;
import java.util.Objects;

public class ArithmeticAggTask implements ResultTask<Double>, Serializable {

    private Dataset<Row> csv;
    private String row;
    private String type;
    private Double total;

    public ArithmeticAggTask(String type, String row) {
        this.type = type;
        this.row = row;
    }

    @Override
    public Double getValue() {
        return this.total;
    }

    public void setCsv(Dataset<Row> csv) {
        this.csv = csv;
    }

    @Override
    public void run() {
        double value = 0;
        if (Objects.equals(this.type, "Sum")) {

            value = this.csv
                    .withColumn(this.row, col(this.row).cast("double"))  // Convierte la columna a double
                    .agg(sum(this.row)).as("Total" + this.row)
                    .first().getDouble(0);

        } else if (Objects.equals(this.type, "Average")) {

            value = this.csv.agg(avg(this.row)).as("Average" + this.row).first().getDouble(0);


        } else if (Objects.equals(this.type, "Desviation")) {

            value = this.csv.agg(stddev(this.row)).as("Desviacion" + this.row).first().getDouble(0);
        }else if (Objects.equals(this.type, "Count")){
            value = this.csv.count();
        }
        this.total = Math.round(value * 100.0) / 100.0;
    }
}
