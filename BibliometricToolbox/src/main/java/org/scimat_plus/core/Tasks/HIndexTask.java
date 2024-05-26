package org.scimat_plus.core.Tasks;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.task.ResultTask;

import static org.apache.spark.sql.functions.*;
import java.io.Serializable;

public class HIndexTask implements ResultTask<Double>, Serializable {
    private Dataset<Row> csv;
    private Double index;

    public HIndexTask(){}
    @Override
    public Double getValue(){return this.index;}

    public void setCsv(Dataset<Row> csv){this.csv = csv;}

    @Override
    public void run(){
        System.out.println("Calculando índice H");

        double value = 0;

        //Ordenar de mayor numero de citas a menor
        this.csv = this.csv.orderBy(col("Citations").desc());

        //Se crea una columna nueva y un id único asignado a cada fila
        Dataset<Row> indexColumn = this.csv.withColumn("Index", monotonically_increasing_id().plus(1));

        //Nos quedamos con las columnas donde el índice es menor o igual al valor de "citations"
        value = Math.toIntExact(indexColumn.filter(col("Index").leq(col("Citations"))).count());

        this.index = Math.round(value*100)/100.0;

    }
}
