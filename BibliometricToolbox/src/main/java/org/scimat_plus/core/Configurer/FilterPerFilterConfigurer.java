package org.scimat_plus.core.Configurer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.scimat_plus.core.Tasks.FilterPerFilterTask;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.Argument;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.arguments.ArgumentStore;
import org.scimat_plus.core.taskgraph.TaskDependecyGraph.mapper.ConfigureTask;

public class FilterPerFilterConfigurer implements ConfigureTask<FilterPerFilterTask> {
    SparkSession spark = SparkSession.builder().
            master("local[*]").getOrCreate();
    Dataset<Row> csv =spark.read().format("csv")
            .option("sep", ";")
            .option("inferSchema", "true")
            .option("header", "true")
            .load("C:/Users/34691/Desktop/Defect.csv");
    @Override
    public void setup(FilterPerFilterTask task, ArgumentStore arguments){
        task.setCsv((Dataset<Row>) arguments.getArgument("a").orElse(new Argument<Dataset<Row>>(csv)).getValue());
    }
}
